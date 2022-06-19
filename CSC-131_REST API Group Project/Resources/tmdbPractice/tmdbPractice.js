async function tmdbRequest(search){
    const apiKey = "2c1f532b5ae9cd29e9cd937bcc1861b7";
    let movieUrl = "https://api.themoviedb.org/3/search/person?api_key=" + apiKey;

    srchUrl = movieUrl + "&query=" + search + "&primary_release_year=2010";

    
    console.log(srchUrl);

    let response = await fetch("https://api.themoviedb.org/3/search/person?api_key=2c1f532b5ae9cd29e9cd937bcc1861b7&query=Amy%20Adams&primary_release_year=2010");
    let data = await response.json();

    console.log(data);


}

tmdbRequest("Amy Adams");