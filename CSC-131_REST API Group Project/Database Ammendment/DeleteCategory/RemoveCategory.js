const cut = ["ENGINEERING EFFECTS","ASSISTANT DIRECTOR","IRVING G. THALBERG MEMORIAL AWARD","SPECIAL FOREIGN LANGUAGE FILM AWARD","HONORARY FOREIGN LANGUAGE FILM AWARD","JEAN HERSHOLT HUMANITARIAN AWARD","MEDAL OF COMMENDATION","GORDON E. SAWYER AWARD","JOHN A. BONNER MEDAL OF COMMENDATION"];


async function Data(){  // using fetch to test on chrome console
    const obj = 'RemoveData.JSON'
    const response = await fetch(obj);
    const oscarsData = await response.json();

    return oscarsData;
}

async function remove()
{
    let data = await Data();
for(var j = 0; j < cut.length; j++)
{
    for(var i = 0; i < data.length; i++)
    {
        if(data[i].category == cut[j])      // check 
        {
            
            //delete data[i];
               //working code
                 data.splice(i, 1);       // delete obj with category
                 i--;
                
        }

    }
    
}

return data;
}

remove().then( newData  => console.log(newData));
