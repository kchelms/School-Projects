const api_URL = 'https://api.wheretheiss.at/v1/satellites/25544'
        
async function getData() {
    const response = await fetch(api_URL);
    const data = await response.json();
    const {latitude, longitude} = data;
    document.getElementById('lat').textContent = latitude;
    document.getElementById('lon').textContent = longitude;
}

getData();
