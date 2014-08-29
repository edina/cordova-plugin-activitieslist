var exec = require('cordova/exec');

exports.byIntent = function(intentString, success, error) {
    exec(success, error, "ActivitiesList", "byIntent", [intentString]);
};
