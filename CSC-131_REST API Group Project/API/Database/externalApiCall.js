async function singleton(singleSearch){
    const omdbKey = '903ccd22';
    const tmdbKey = '2c1f532b5ae9cd29e9cd937bcc1861b7';
    let fetch = require('node-fetch');

    let omdbUrl = "http://www.omdbapi.com/?apikey=" + omdbKey + "&t=" + singleSearch.movie;
    let tmdbUrl = 'https://api.themoviedb.org/3/movie/' + singleSearch.tid + '?api_key=' + tmdbKey;

    //fetch ratings, plot, runtime, and other
    let res = await fetch(omdbUrl);
    let json = await res.json();
    if(json.hasOwnProperty('Error')){
        fetch(tmdbUrl)
        .then(res => res.json())
        .then(json => {

            singleSearch.ratings = json.popularity;
            singleSearch.plot = json.overview;
            singleSearch.time = json.runtime;
        })
    }
    else{
    singleSearch.ratings = json.Ratings;
    singleSearch.plot = json.Plot;
    singleSearch.time = json.Runtime;
    singleSearch.director = json.Director;
    singleSearch.cast = json.Actors;
    }

    tmdbUrl = 'https://api.themoviedb.org/3/movie/' + singleSearch.tid + '/watch/providers?api_key=' + tmdbKey;
    
    //fetch wath providers
    res = await fetch(tmdbUrl) 
    json = await res.json();
    
    if(!json.results.hasOwnProperty('US')){
        singleSearch.watchProvider = "No Watch Providers Available";
    }
    else{
        singleSearch.watchProvider = json.results.US.link;
        }

    return singleSearch;

    }

async function singletonDeliver(someObject){
    let data = await singleton(someObject);

    return data;
}

module.exports = singletonDeliver;