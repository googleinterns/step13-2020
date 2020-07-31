
// Product Name & Rating
var ratings = {
    rating: 0
}
 
// Total Stars
const starsTotal = 5;

// Get ratings
function getRatings(value) {

    ratings.rating = value;

    for (let rating in ratings) {
    // Get percentage
    const starPercentage = (ratings[rating] / starsTotal) * 100;
 
    // Round to nearest 10
    const starPercentageRounded = `${Math.round(starPercentage / 10) * 10}%`;
 
    // Set width of stars-inner to percentage
    document.querySelector(`.stars-inner`).style.width = starPercentageRounded;
 
    // Add number rating
    document.querySelector(`.number-rating`).innerHTML = ratings[rating];
    }
}
