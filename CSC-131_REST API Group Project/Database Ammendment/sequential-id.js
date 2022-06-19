async function fetchOscarsData() {
    let res = await fetch('./FinalJSON.json');
    let data = await res.json();

    return data;
}

async function uniqueCategories() {
    let oscarsData = await fetchOscarsData();

    let ID = 0;

    oscarsData.forEach(element => {
        element.ID = ID;

        ID++;
    });

    return oscarsData;
}

uniqueCategories().then(data => console.log(data));