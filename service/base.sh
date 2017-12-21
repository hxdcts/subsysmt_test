#!/bin/bash
function is_exist_func(){
  if hash $1 2>/dev/null; then
    $1 "$2"
  else
     echo 'this func is not exist'
  fi
 }

function is_exist_dir(){
   if [ -d $1 ]; then
        echo "true"
   else
        echo "false"
   fi
}

function run_cmd(){
    local time=`date`
   echo "$time: $1"
   eval $1
}

function ensure_dir(){
    if [ ! -d $1 ]; then
    run_cmd "mkdir -p  $1"
    fi
}

function action(){
    echo "fun action params:$@"
    is_exist_func $@
}

action $@
