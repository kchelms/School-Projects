//Checks each query object key for validity
//Formats valid queries for search function
//Returns null if any are invalid
//Returns reformatted object if none are invalid
function validate(queryOBJ) {
    if(queryOBJ.hasOwnProperty('year'))             queryOBJ.year           = yearValidate(queryOBJ.year); 
    if(queryOBJ.hasOwnProperty('winner'))           queryOBJ.winner         = winnerValidate(queryOBJ.winner);
    if(queryOBJ.hasOwnProperty('category'))         queryOBJ.category       = checkArr(queryOBJ.category);
    if(queryOBJ.hasOwnProperty('general_cat'))      queryOBJ.general_cat    = checkArr(queryOBJ.general_cat);
    if(queryOBJ.hasOwnProperty('entity'))           queryOBJ.entity         = checkArr(queryOBJ.entity);

    if(Object.values(queryOBJ).includes(null)) return {};
    
    else return queryOBJ;
}

//Checks the year query for validity, formats array for search function
function yearValidate(yearKey) {
    yearKey = checkArr(yearKey);

    if( !yearKey ||
        yearKey.length > 2 || 
        yearKey.some(e => e < 1927 || e > 2017)) return null;
    
    yearKey = yearKey.map(e => parseInt(e));

    //Single year search case
    if(yearKey.length == 1) return yearKey;

    let yrArr = [];
    let yrArrLen = 1 + Math.abs(yearKey[1] - yearKey[0]);

    //Makes query order irrelevant
    let startYr = yearKey[0] < yearKey[1] ? yearKey[0] : yearKey[1];

    //Creates new array; changes format from:
    // [firstYr, lastYr] to [firstYr, ..., lastYr]
    for(let i = 0; i < yrArrLen; i++) yrArr.push(startYr + i);

    return yrArr;
}

//Checks for a valid winner key value, converts the value to true or false
function winnerValidate(winnerKey){
    if (winnerKey < 0 || winnerKey > 1) return null;

    return winnerKey == 1; 
}

//Checks that the key's value is stored in an array
function checkArr(key){
    if(Array.isArray(key)) return key;
    
    else return null;
}

module.exports = validate;