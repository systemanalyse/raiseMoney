<?php
$file = $_FILES["img"];
if ($file["error"] == 0) {
 $typeArr = explode("/", $file["type"]);
 if($typeArr[0]== "image"){
  $imgType = array("png","jpg","jpeg");
  if(in_array($typeArr[1], $imgType)){
   $imgname = "picture/".time().".".$typeArr[1];
   $bol = move_uploaded_file($file["tmp_name"], $imgname);
   if($bol){
    echo $imgname;
   } else {
    echo "Fail!";
   };
  };
 } else {
  echo "Fail";
 };
} else {
 echo $file["error"];
};
?>
