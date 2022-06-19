//here we call installed modules and assigning them to vars
//express and bodyParser respesctively
var express         = require("express");
var bodyParser      = require("body-parser");

//these two lines tells express to accept both JSON and URL
var routes = require("./routes.js");
var app = express();

//Here we defined the port where our server is running on
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

routes(app);

var port = process.env.PORT || 80

var server = app.listen(port, function () {
    console.log("app running on port.", server.address().port);
});