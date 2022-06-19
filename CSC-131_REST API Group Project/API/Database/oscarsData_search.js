//Filters results using any combination of queries:
//  Single Object ID
//  Award winner or loser
//  Award category *
//  General award category (group of related / redundant categories) *
//  Nominee *
//  Year nominated *
//
//  * - multiple queries per search permitted 
async function aggregatedSearch(inputJSON) {
    let result = require("./oscarsData.json");
    
    if(inputJSON.hasOwnProperty('ID'))              result = await singleton(result, inputJSON.ID);
    if(inputJSON.hasOwnProperty('winner'))          result = search(result, "winner", inputJSON.winner);
    if(inputJSON.hasOwnProperty('category'))        result = rangeSearch(result, "category", inputJSON.category);
    if(inputJSON.hasOwnProperty('general_cat'))     result = rangeSearch(result, "general_cat", inputJSON.general_cat);
    if(inputJSON.hasOwnProperty('entity'))          result = rangeSearch(result, "entity", inputJSON.entity);
    if(inputJSON.hasOwnProperty('year'))            result = rangeSearch(result, "year", inputJSON.year);

    return result;
}

//Returns an array of objects matching the query
function search (oscarsData, searchKey, query) {
    let result = [];

    oscarsData.forEach(element => {
        if(element[searchKey] == query) result.push(element);
    });

    return result;
}

//Calls search() for multiple queries of the same key and concatenates the results
function rangeSearch(oscarsData, searchKey, query){
    let result = [];
    
    query.forEach(element => {
        result = result.concat(search(oscarsData, searchKey, element));
    });

    return result;
}

//Locates singleton results by ID if valid, and
//calls external APIs for more detailed singleton results
async function singleton(oscarsData, ID){
    if(ID < 0 || ID > oscarsData.length) return null;

    let externAPI = require('./externalApiCall.js');

    let result = oscarsData[ID];

    return await externAPI(result);
}

module.exports = aggregatedSearch;