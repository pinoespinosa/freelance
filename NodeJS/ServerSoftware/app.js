var express = require("express"),
    app = express(),
    bodyParser = require("body-parser"),
    methodOverride = require("method-override");
mongoose = require('mongoose');

app.use(bodyParser.urlencoded({
    extended: false
}));
app.use(bodyParser.json());
app.use(methodOverride());

var router = express.Router();


app.all("/*", function(req, res, next){
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Methods', '*');
  res.header('Access-Control-Allow-Headers', '*');
  next();
});

app.post("/saveFile", function(request, res) {
    console.log(request.body); //This prints the JSON document received (if it is a JSON document)
    console.log('holaa')


    var fs = require('fs');
    var stream = fs.createWriteStream("my_file.txt");
    stream.once('open', function(fd) {
        stream.write("My first row\n");
        stream.write("My second row\n");
        stream.end();
    });

    res.writeHead(200, {
        'Content-Type': 'text/plain'
    });
    res.end('Hello World\n');

});

router.get('/', function(req, res) {



});

app.use(router);

app.listen(3000, function() {
    console.log("Node server running on http://localhost:3000");
});