var exec = require('cordova/exec');

exports.checkIntent = function(str, success, error) {
    exec(success, error, "CheckIntent", "check", [str]);
};
