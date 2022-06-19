async function fetchOscarsData() {
    let res = await fetch('./data.json');
    let data = await res.json();

    return data;
}

async function uniqueCategories() {
    let oscarsData = await fetchOscarsData();

    let categories = [];
    
    oscarsData.forEach(element => {
        categories.push(element.category);        
    });
    
    let ret = Array.from(new Set(categories));

    return ret;
}

uniqueCategories().then(arr => console.log(arr));