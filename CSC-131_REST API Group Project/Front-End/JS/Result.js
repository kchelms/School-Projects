

async function getSingle(i_d)
{
    var api_URL = "http://localhost:3000/?ID=" + i_d;
    console.log(api_URL);
    
    const response = await fetch(api_URL);
    const data_s = await response.json();

    return data_s;
}