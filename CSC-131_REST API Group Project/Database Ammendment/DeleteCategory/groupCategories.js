const general_cat =
 [
    ["ACTOR","ACTOR", "ACTOR IN A LEADING ROLE"], 
    ["ACTRESS", "ACTRESS", "ACTRESS IN A LEADING ROLE"],
    ["DIRECTING", "DIRECTING (Comedy Picture)", "DIRECTING (Dramatic Picture)", "DIRECTING"],
    ["PICTURE", "OUTSTANDING PICTURE","UNIQUE AND ARTISTIC PICTURE","OUTSTANDING MOTION PICTURE","BEST MOTION PICTURE","BEST PICTURE"],
     ["SHORT FILM","SHORT SUBJECT (Cartoon)","SHORT SUBJECT (Comedy)","SHORT SUBJECT (Novelty)","SHORT SUBJECT (Color)","SHORT SUBJECT (One-reel)","SHORT SUBJECT (Two-reel)","SHORT SUBJECT (Live Action)","SHORT SUBJECT (Animated)", "SHORT FILM (Animated)", "SHORT FILM (Live Action)", "SHORT FILM (Dramatic Live Action)"],
    ["WRITING (Original)", "WRITING (Original Story)","WRITING", "WRITING (Screenplay)","WRITING (Original Screenplay)","WRITING (Original Motion Picture Story)","WRITING (Motion Picture Story)","WRITING (Story and Screenplay)", "WRITING (Screenplay--Original)", "WRITING (Story and Screenplay--written directly for the screen)","WRITING (Story and Screenplay--based on material not previously published or produced)","WRITING (Story and Screenplay--based on factual material or material not previously published or produced)","WRITING (Screenplay Written Directly for the Screen--based on factual material or on story material not previously published or produced)","WRITING (Screenplay Written Directly for the Screen)"],
    ["WRITING (Adaptation)","WRITING (Adaptation)","WRITING (Screenplay--Adapted)","WRITING (Screenplay--based on material from another medium)","WRITING (Screenplay Adapted from Other Material)","WRITING (Screenplay Based on Material from Another Medium)","WRITING (Screenplay Based on Material Previously Produced or Published)","WRITING (Adapted Screenplay)"],
    ["MUSIC" ,"MUSIC (Scoring)", "MUSIC (Song)", "MUSIC (Original Score)", "MUSIC (Music Score of a Dramatic Picture)","MUSIC (Scoring of a Musical Picture)","MUSIC (Music Score of a Dramatic or Comedy Picture)","MUSIC (Music Score--substantially original)","MUSIC (Scoring of Music--adaptation or treatment)","MUSIC (Original Music Score)","MUSIC (Original Score--for a motion picture [not a musical])","MUSIC (Score of a Musical Picture--original or adaptation)","MUSIC (Song--Original for the Picture)","MUSIC (Original Song Score)", "MUSIC (Original Dramatic Score)","MUSIC (Scoring: Adaptation and Original Song Score)","MUSIC (Scoring: Original Song Score and Adaptation -or- Scoring: Adaptation)", "MUSIC (Original Song)","MUSIC (Original Song Score and Its Adaptation or Adaptation Score)","MUSIC (Adaptation Score)","MUSIC (Original Song Score and Its Adaptation -or- Adaptation Score)","MUSIC (Original Song Score or Adaptation Score)","MUSIC (Original Musical or Comedy Score)"],
    ["CINEMATOGRAPHY","CINEMATOGRAPHY","CINEMATOGRAPHY (Black-and-White)","CINEMATOGRAPHY (Color)"],
    ["ART DIRECTION","ART DIRECTION","ART DIRECTION (Black-and-White)","ART DIRECTION (Color)"],
    ["OUTSTANDING PRODUCTION","OUTSTANDING PRODUCTION","PRODUCTION DESIGN"],
    ["EFFECTS","SPECIAL EFFECTS","SPECIAL VISUAL EFFECTS","VISUAL EFFECTS"],
    /*["MAKEUP", "MAKEUP", "MAKEUP AND HAIRSTYLING"​​​],
    ["COSTUME","COSTUME DESIGN (Black-and-White)","COSTUME DESIGN (Color)"​​​,"COSTUME DESIGN"],
    ["SOUND","SOUND RECORDING"​​​,"SOUND","SOUND EFFECTS","SOUND EFFECTS EDITING","SOUND EDITING","SOUND MIXING"],
    [ "DOC" , "DOCUMENTARY (Short Subject)" , "DOCUMENTARY" , "DOCUMENTARY (Feature)"​]​​*/
];
 
var fs  = require("fs");
//const { stringify } = require("querystring");
// var gracefulFs = require("graceful-fs");
// gracefulFs.gracefulify(fs);
var data= require("./NewJSON.json");


function groupCategories()
{
    
for(let i = 0; i < general_cat.length; i++)
{
       
    for(let j = 0; j < general_cat[i].length; j++)
    {
        var key = general_cat[i][0];
    
        let newkey = {"general_cat" : key};

        
      for(let k = 0; k < data.length; k++)
      {
          
                if(data[k].category === general_cat[i][j] )
                {
                Object.assign(data[k], newkey);
            
                fs.writeFile(data, JSON.stringify(data), (err) => {     // adds more content then needed but manualy deleted it 
                    if (err) {                                                          
                        console.error(err);
                        return;
                    }
                    
                 });

                };
        }
        
        
    
     }
}

    //console.log(data)
return data;
}
console.log(groupCategories());
