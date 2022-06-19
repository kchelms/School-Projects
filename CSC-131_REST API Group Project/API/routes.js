let cors            = require("cors");
let validate        = require("./validateQuery.js");
let search          = require("./Database/oscarsData_search.js");



//API Endpoints
let appRouter = function(app){
 
    //Singleton response endpoint
    app.get("/ID/:ID", cors(), async function(req,res){
        if(req.params.ID >= 0){ 
            let result = await search(req.params);

            //Response for valid ID
            if(result) res.status(200).send(result);

            //Response for invalid ID
            else res.status(400).send('Invalid Request');
        }
    });
    
    //Muli-object response endpoint
    app.get('/search/', cors(), async function(req,res){
    
        //Place query into corresponding database keys
        let queryOBJ = {};

        if(req.query.hasOwnProperty('w'))       queryOBJ.winner         = req.query.w;
        if(req.query.hasOwnProperty('yr'))      queryOBJ.year           = req.query.yr;     
        if(req.query.hasOwnProperty('gc'))      queryOBJ.general_cat    = req.query.gc;
        if(req.query.hasOwnProperty('c'))       queryOBJ.category       = req.query.c;
        if(req.query.hasOwnProperty('e'))       queryOBJ.entity         = req.query.e;

        //Validate query and format values for database search
        queryOBJ = validate(queryOBJ);

        //Responses for valid query
        if(JSON.stringify(queryOBJ) !== '{}') {
            let result = await search(queryOBJ);

            if(result.length) res.status(200).send(result);

            else res.status(404).send('Result Not Found');
        }

        //Response for invalid query
        else res.status(400).send('Invalid Request');
    });
}

module.exports = appRouter;