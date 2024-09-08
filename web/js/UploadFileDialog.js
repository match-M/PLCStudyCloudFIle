var topic = false;
var ladderDiagram = false;
var touchScreen = false;
var isLoad = false;
var dialogRoot;
var li_topic;
var li_ladderDiagram;
var li_touchScreen;

function load(){
    alert("load");
    dialogRoot = document.getElementsByClassName("dialog_root")[0];
    li_topic = document.getElementById("li-topic");
    li_ladderDiagram = document.getElementById("li-ladder-diagram");
    li_touchScreen = document.getElementById("li-touch-screen");
    isLoad = true;
}
function chooseTopic(){
    topic = true;
    ladderDiagram = false;
    touchScreen = false;

    li_topic.style.backgroundColor = "deepskyblue";
    li_ladderDiagram.style.backgroundColor = "white";
    li_touchScreen.style.backgroundColor = "white";
}

function chooseLadderDiagram(){
    topic = false;
    ladderDiagram = true;
    touchScreen = false;

    li_topic.style.backgroundColor = "white";
    li_ladderDiagram.style.backgroundColor = "deepskyblue";
    li_touchScreen.style.backgroundColor = "white";
}

function chooseTouchScreen(){
    topic = false;
    ladderDiagram = false;
    touchScreen = true;

    li_topic.style.backgroundColor = "white";
    li_ladderDiagram.style.backgroundColor = "white";
    li_touchScreen.style.backgroundColor = "deepskyblue";
}

function getChooseFileName(){
    var chooseFile = document.getElementById("file").files[0];
    var showFileNameId = document.getElementById("file-name");
    showFileNameId.innerHTML = chooseFile.name;
}

function uploadFile(){
    var chooseFile = $("#file")[0].files[0];
    var projectName = $("#project-name").val();
    var fileType = "0";
    var formData = new FormData();//必须使用FormData表达上传数据

    if(topic) fileType = "0";
    else if(ladderDiagram) fileType = "1";
    else if(touchScreen) fileType = "2";

    formData.append("file", chooseFile);
    formData.append("projectName", projectName);
    formData.append("type", fileType);

    $.ajax({
        method:"post",
        url:"/plc/uploadFile",
        data:formData,
        processData: false,
        contentType: false,
        dataType:"json",
        success:function(data){
            alert(data.msg);
            closeDialog();
        },
        error:function(err){
            console.log("error："+err);
        }
    });
}

function uploadFileDialog(){
    if(!isLoad) load();
    dialogRoot.style.display = "block";
}

function closeDialog(){
    dialogRoot.style.display = "none";
}