const cut = ["ENGINEERING EFFECTS","ASSISTANT DIRECTOR","IRVING G. THALBERG MEMORIAL AWARD","SPECIAL FOREIGN LANGUAGE FILM AWARD","HONORARY FOREIGN LANGUAGE FILM AWARD","JEAN HERSHOLT HUMANITARIAN AWARD","MEDAL OF COMMENDATION","GORDON E. SAWYER AWARD","JOHN A. BONNER MEDAL OF COMMENDATION","SCIENTIFIC OR TECHNICAL AWARD (Class I)","SCIENTIFIC OR TECHNICAL AWARD (Class II)","SCIENTIFIC OR TECHNICAL AWARD (Class III)","SCIENTIFIC AND TECHNICAL AWARD (Special Award)","SCIENTIFIC AND TECHNICAL AWARD (Academy Award of Merit)","SCIENTIFIC OR TECHNICAL AWARD (Scientific and Engineering Award)","SCIENTIFIC OR TECHNICAL AWARD (Technical Achievement Award)","HONORARY AWARD","SPECIAL AWARD","AWARD OF COMMENDATION"];



// async function Data(){  // using fetch to test on chrome console
//     const obj = 'refrence.json'
//     const response = await fetch(obj);
//     const oscarsData = await response.json();

//     return oscarsData;
// }
var fs = require("fs");
var data = require("./refrence.json");


 function remove()
{
    //let newData = {};
    // let data = await Data();
for(var j = 0; j < cut.length; j++)
{
    for(var i = 0; i < data.length; i++)
    {
        if(data[i].category == cut[j])      // check 
        {
       
               //working code
                 data.splice(i, 1); // 
                 i--;
                //  fs.writeFile("./RemoveData.json", JSON.stringify(data), (err) => {     // adds more content then needed but manualy deleted it 
                //     if (err) {                                                          
                //         console.error(err);
                //         return;
                //     };
                //    // console.log("File has been created");
                // });
               
        }
        // else if(data[i].category != cut[j])// checking to see if the objects were deleted
        // {
        //     console.log(data[i].category);
        //     console.log(i);
        // }
  
    
    }
    
}

return data;
}

console.log(remove());
//remove().then( newData  => console.log(newData));