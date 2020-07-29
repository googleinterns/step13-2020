gapi.load("client", loadClient);

function loadClient() {
    gapi.client.setApiKey("AIzaSyAHduDbTPxThjGueKTvIaJHNMV8SRLyje0");
    return gapi.client.load("https://www.googleapis.com/discovery/v1/apis/youtube/v3/rest")
        .then(function() { console.log("GAPI client loaded for API"); },
              function(err) { console.error("Error loading GAPI client for API", err); });
}

const ytForm = document.getElementById('yt-form');
const keywordInput = document.getElementById('keyword-input');
const maxresultInput = document.getElementById('maxresult-input');
const orderInput = document.getElementById('order-input');
const videoList = document.getElementById('videoListContainer');
const videoTitle = document.getElementById('videoTitleContainer');
var pageToken = '';

ytForm.addEventListener('submit', e => {
    e.preventDefault();
    execute();
});
 
function execute() {
    const searchString = keywordInput.value;
    const maxresult = maxresultInput.value;
    const orderby = orderInput.value;

    var arr_search = {
        "part": 'snippet',
        "type": 'video',
        "order": orderby,
        "maxResults": maxresult,
        "q": searchString
    };
 
    if (pageToken != '') {
        arr_search.pageToken = pageToken;
    }
    return gapi.client.youtube.search.list(arr_search)
    .then(function(response) {
        const listItems = response.result.items;
        if (listItems) {
            let output = '';
            let outputTitles = '';
            listItems.forEach(item => {
                const videoId = item.id.videoId;
                const videoTitle = item.snippet.title;
                var shortTitle = videoTitle.substring(0, videoTitle.length);
                output += `<div class="item"><a data-fancybox href="https://www.youtube.com/watch?v=${videoId}"><img width="100" height="100" src="http://i3.ytimg.com/vi/${videoId}/hqdefault.jpg" /><p>${shortTitle}</p></a></div>`
            });
            output+='<br>';
            videoList.innerHTML = output;
            videoTitle.innerHTML = outputTitles;
        }
    },
    function(err) { console.error("Execute error", err); });
}