
//retrieves our database that has had it's categories grouped into more general categories
async function dRetrieval(){
    const database = 'groupedData.json';
    const response = await fetch(database);
    const data = await response.json();

    return data;
}
//tmdb api key
const apiKey = "2c1f532b5ae9cd29e9cd937bcc1861b7";

//searches tmdb for either a person or movie using a name or title
async function tmdb_keywordSearch(searchType, keyword){    
    let movieUrl = "https://api.themoviedb.org/3/search/" + searchType + "?api_key=" + apiKey;
    
    movieUrl += "&query=" + keyword;
    
    let res = await fetch(movieUrl);
    let dat = await res.json();

    return dat;
}

//searches tmdb for either a person or movie using an id provided by tmdb
async function tmdb_searchByID(searchType, tmdb_ID){    
    let movieUrl = "https://api.themoviedb.org/3/" + searchType + "/" + tmdb_ID + "?api_key=" + apiKey;
    
    
    let res = await fetch(movieUrl);
    let dat = await res.json();

    return dat;
}

//searches tmdb for a list of movies that a person has been in using their id from tmdb
async function creditSearch(personID){
    let movieUrl = "https://api.themoviedb.org/3/person/" + personID + "/movie_credits?api_key=" + apiKey;

    let res = await fetch(movieUrl);
    let dat = await res.json();

    return dat; 
}

//turns the string provided by tmdb for the movie's year into an integer
function parseDateString(dateString){
    let resDateString = dateString
    let resYearString = resDateString.slice(0,4);

    return parseInt(resYearString, 10);
}

async function tDataAmmend(database){
    for(let d = 0; d < database.length; d++){

        //makes a tmdb request for a movie using the entity category of the current 
        //database entry
        dat = await tmdb_keywordSearch("movie", database[d].entity);
        
        let resLength = dat.results.length;        
        
        
        for(let r = 0; r < resLength; r++){
            //checks that the current result from tmdb has a release date and checks it against
            //the year for the current database entry
            if(dat.results[r].hasOwnProperty('release_date')){
                let resYearNum = parseDateString(dat.results[r].release_date);

                if(database[d].year === resYearNum){
                    database[d].tid = dat.results[r].id;
                    database[d].movie = dat.results[r].title;
                    database[d].poster = dat.results[r].poster_path;
                    
                    //searches a movie by it's id if the years match and adds the imdb link to the 
                    //current database entry
                    let dat2 = await tmdb_searchByID("movie", dat.results[r].id);

                    database[d].imdbLink = "https://www.imdb.com/title/" + dat2.imdb_id;
                }                        
            }
        }
        
        //checks if the current database entry has been found by the movie search and has been ammended
        //other wise it searches the current database entry through person search 
        if(!database[d].hasOwnProperty('tid')){
            database[d] = await personSearch(database[d]);
        }
    }
    
    return database;
}   

async function personSearch(database){

    //makes a tmdb request for a person using the entity category of the current 
    //database entry
    dat = await tmdb_keywordSearch("person", database.entity);

    if(dat.results.length){
        for(let r = 0; r < dat.results.length; r++){
            //makes a search using the current resutls person id
            //to get a list of most of the movies they were in
            let dat3 = await creditSearch(dat.results[r].id);
            
            if(dat3.hasOwnProperty('cast')){
                for(let cast = 0; cast < dat3.cast.length; cast++){
                    //check the release date for the current movie against
                    //the year of the current database entry
                    if(dat3.cast[cast].hasOwnProperty('release_date')){
                        let resYearNum = parseDateString(dat3.cast[cast].release_date);

                        if(database.year === resYearNum){
                            database.tid = dat3.cast[cast].id;
                            database.poster = dat3.cast[cast].poster_path;
                            //searches a movie by it's id if the years match and adds the imdb link to the 
                            //current database entry
                            let dat2 = await tmdb_searchByID("movie", database.tid);
                            
                            database.imdbLink = "https://www.imdb.com/title/" + dat2.imdb_id;
                            database.movie = dat2.title;
                            
                            return database;
                        }
                    }
                }
            }
            //same thing as cast but checking through crew for directors and other movie crew people
            if(!database.hasOwnProperty('movie') && dat3.hasOwnProperty('crew')){
                for(let crew = 0; crew < dat3.crew.length; crew++){

                    if(dat3.crew[crew].hasOwnProperty('release_date')){
                        let resYearNum = parseDateString(dat3.crew[crew].release_date);

                        if(database.year === resYearNum){
                            database.tid = dat3.crew[crew].id;
                            database.poster = dat3.crew[crew].poster_path;
                            //searches a movie by it's id if the years match and adds the imdb link to the 
                            //current database entry
                            let dat2 = await tmdb_searchByID("movie", database.tid);
                            
                            database.imdbLink = "https://www.imdb.com/title/" + dat2.imdb_id;
                            database.movie = dat2.title;
                            
                            return database;
                        }
                    }
                }
            }
        }
    }  
    
    return database;
}

//used to grab the data from the data retrieval and ammendment functions in order to log it to the console
async function run(){
    let data = await dRetrieval();

    let ammend = await tDataAmmend(data);
    
    return ammend;
} 
//stringifies and logs the json for easy copy and paste into another json file
run().then(data => console.log(JSON.stringify(data)));