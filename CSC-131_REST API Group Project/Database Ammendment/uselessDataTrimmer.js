async function trim(){
    let res = await fetch('testData.json');
    let dat = await res.json();


    for(let i = 0; i < dat.length; i++){
      
        if(!dat[i].hasOwnProperty('movie')){
            
            dat.splice(i , 1);
            i--;
        }
    }
    console.log(JSON.stringify(dat));
    
}
trim();