function getAllFile(){
    var fileInfos = "error";
    $.ajax({
        method:"get",
        url:"/plc/getFileInfo",
        contentType:false,
        async: false,
        success:function(data){
            fileInfos = data
        },
        error:function(err){
            console.log("error："+err);
        }
    });

    return fileInfos;
}

function uploadFile(){

}