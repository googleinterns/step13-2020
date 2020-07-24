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
            let output = '<h4>Videos</h4><ul>';
            listItems.forEach(item => {
                const videoId = item.id.videoId;
                const videoTitle = item.snippet.title;
                output += `<a data-fancybox href="https://www.youtube.com/watch?v=${videoId}"><img style="30px; white;" width="100" height="50" src="http://i3.ytimg.com/vi/${videoId}/hqdefault.jpg" /></a>`;
            });
            videoList.innerHTML = output;
        }
    },
    function(err) { console.error("Execute error", err); });
}