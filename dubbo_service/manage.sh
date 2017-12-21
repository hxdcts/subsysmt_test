#!/bin/bash
set -e


project_name=$2
dubbo_image="docker-publish.sunfund.com/dubbo_$project_name:$4"
dubbo_container_name=${project_name}"_dubbo"
dubbo_port=$3
docker_logs_dir=/app_logs
dubbo_logs_dir="/Users/cts/Desktop/data/jenkins-payment/runtime/logs/$project_name"
#maven_m2_dir="/opt/data/jenkins-project/persistent/m2/repository"
project_dir="$(pwd)"
function build(){
ensure_dir $dubbo_logs_dir
_build
    }

    function _build(){
    local dir="$project_dir/$project_name/target"
    run_cmd "cp -r $dir/lib $project_dir/lib"
    run_cmd "cp  $dir/$project_name.jar $project_dir/dubbo_provider.jar"
    build_image $dubbo_image
    #tag_image $dubbo_image
    push_image $dubbo_image
}

function deploy(){
remove_container $dubbo_container_name
pull_image $dubbo_image
run_dubbo_container
    }

    function build_image(){
    run_cmd "docker build -f $project_dir/dubbo_service/Dockerfile  -t $1 ."
}

function tag_image(){
run_cmd "docker tag $1 $1"
       }

       function push_image(){
         run_cmd "docker push $1"
     }

     function pull_image(){
       run_cmd "docker pull $1"
   }

   function start_container(){
     run_cmd "docker start $1"
 }

 function stop_container(){
   run_cmd "docker stop $1"
   }

   function restart_container(){
     run_cmd "docker restart $1"
 }

 function remove_image(){
   run_cmd "docker rmi $1"
   }

   function remove_container(){
     local container_name=$1
       if [[ `docker ps | grep $container_name` ]]; then
                   stop_container $container_name
                     fi
                       if [[ `docker ps -a | grep $container_name` ]]; then
                           run_cmd "docker rm $container_name"
                       fi
                   }

                   function run_dubbo_container(){
                   local java_cmd="java -jar /usr/local/dubbo/dubbo_subsysmt_provider.jar > $docker_logs_dir/$project_name"'.log'
                   local args='--restart=always'
                   args="$args --name $dubbo_container_name"
                   args="$args -v $dubbo_logs_dir:$docker_logs_dir"
                   args="$args --net='host'"
                   args="$args -m 1g"
                   args="$args -c 512"
                   args="$args $dubbo_image"
                   run_cmd "docker run -d $args sh -c \"$java_cmd\"" 
               }

           . $(pwd)/service/base.sh
