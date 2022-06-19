//Pull user input
var f_sing = document.getElementById('')
var in_sing = document.getElementById('x_sing');
var sing_link = document.getElementById('single_res')
var sing = document.getElementById('single');
var form = document.getElementById('acc');
var yearStart = document.getElementById('m_start');
var yearEnd = document.getElementById('m_end');
var mname = document.getElementById("m_name");
var bx = document.getElementById("wol");
var res = document.getElementById("res");

//Object decleration
var movie =
{
    entity: null,
    categ: null,
    dates: null,
    winlose: null,
};

//building link for entity
function get_entity()
{
    movie.entity = movie.entity.replaceAll(" ", "+");

    if(movie.entity && movie.categ || movie.entity && movie.dates || movie.entity && movie.winlose)
    {
        return "e[]=" + movie.entity + "&";
    }
    else if(movie.entity)
    {
        return "e[]=" + movie.entity;
    }
    else
    {
        return "";
    }
}

//building link for win or lose
function get_wol()
{
    if(movie.winlose && movie.categ || movie.winlose && movie.dates)
    {
        return "w=" + movie.winlose + "&";
    }
    else if(movie.winlose)
    {
        return "w=" + movie.winlose;
    }
    else
    {
        return "";
    }
}

//building link for dates
function get_dates()
{
    if(movie.dates && movie.dates.length == 2 && movie.categ)
    {
        return "yr[]=" + movie.dates[0] + "&" + "yr[]=" + movie.dates[1] + "&";
    }
    if(movie.dates && movie.dates.length == 2)
    {
        return "yr[]=" + movie.dates[0] + "&" + "yr[]=" + movie.dates[1];
    }
    else if(movie.dates && movie.categ)
    {
        return "yr[]=" + movie.dates + "&";
    }
    else if(movie.dates)
    {
        return "yr[]=" + movie.dates;
    }
    else
    {
        return "";
    }
}

//building link for categories
function get_categories()
{
    var x = "";
    if(movie.categ)
    {
        for(var i = 0; i <= movie.categ.length; i++)
        {

            if(i != movie.categ.length - 1)
            {
                var n = movie.categ[i];
                n = n.replaceAll(" ", "+");

                x = x + "gc[]=" + n + "&";
            }
            else
            {
                var n = movie.categ[i];
                n = n.replaceAll(" ", "+");

                return x + "gc[]=" + n;
            }
        }
    }
    else
    {
        return "";
    }
}

async function getSingle(i_d)
{
    var api_URL = "http://little-bits-final.herokuapp.com/ID/" + i_d;
    console.log(api_URL);
    
    const response = await fetch(api_URL);
    const data_s = await response.json();

    sing_link.setAttribute("href", api_URL);
}

//api sends user input
async function getData()
{
    const d_entity = get_entity();
    const d_w = get_wol();
    const d_dates = get_dates(); 
    const d_cat = get_categories();
    
    var api_URL = "http://little-bits-final.herokuapp.com/search/?" + d_entity + d_w + d_dates + d_cat;
    
    const response = await fetch(api_URL);
    const data = await response.json();

    res.setAttribute("href", api_URL);
}

//Button use to pull all input
form.addEventListener('submit', function(event)
{   
    //Function to find years and push on array
    var nstart = parseFloat(yearStart.value);
    var nend = parseFloat(yearEnd.value);
    
    var years = [];

    if(nstart && nend)
    {
        years = [nstart, nend];
    }
    else if(nstart)
    {
        years = [nstart];
    }
    else if(nend)
    {
        years = [nend];
    }
    else
    {
        years = null;
    }

    //User input whether they won or not
    var wlose;

    if(bx.value == "Win")
    {
        wlose = 1;
    }
    else if(bx.value == "Lose")
    {
        wlose = 0;
    }
    else
    {
        wlose = null;
    }
    
    //Initiate category array and user input on array
    var selected = [];

    for(var option of document.getElementById('cats').options) {
        if(option.selected)
        {
            selected.push(option.value);
        }
    }
    
    if(selected === undefined || selected.length == 0)
    {
        selected = null;
    }
    //Object asignment
    movie.entity = mname.value;
    movie.categ = selected;
    movie.dates = years;
    movie.winlose = wlose;

    getData();

    event.preventDefault();
});

//For singleton results
single.addEventListener('submit', function(event)
{   
    sing_id = in_sing.value;
    
    getSingle(sing_id);

    event.preventDefault();
});