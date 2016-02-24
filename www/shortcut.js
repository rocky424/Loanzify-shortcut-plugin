
module.exports = {
    add: function (name, successCallback, errorCallback) {

 var img = new Image();
    img.crossOrigin = 'Anonymous';
    img.onload = function(){
        var canvas = document.createElement('CANVAS');
        var ctx = canvas.getContext('2d');
        var dataURL;
        canvas.height = this.height;
        canvas.width = this.width;
        ctx.drawImage(this, 0, 0);
        dataURL = canvas.toDataURL();
         cordova.exec(successCallback, errorCallback, "Shortcut", "add", [dataURL]);
        canvas = null; 
    };
    img.src = name;

       
    },
        delete: function (successCallback, errorCallback) {
         cordova.exec(successCallback, errorCallback, "Shortcut", "delete", []);

       
    }
};
