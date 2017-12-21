#!/bin/bash
set -e


project_name=$2
tomcat_image="docker-publish.sunfund.com/tomcat_$project_name:$5"
maven_image="docker.sunfund.com/maven:3.3-jdk-7-alpine"

tomcat_container_name=${project_name}"_tomcat"
dubbo_container_name=${project_name}"_dubbo"
maven_container_name="maven"
profile=$3
tomcat_port=$4
docker_log_dir="/app_logs"
tomcat_logs_dir="/Users/cts/Desktop/data/jenkins-payment/runtime/logs/$project_name"
maven_m2_dir="/Users/cts/Desktop/data/jenkins-project/persistent/m2/repository"
project_dir="$(pwd)"
function build(){
  local profile=$profile
    ensure_dir $tomcat_logs_dir

      mvn $profile
        _build
    }

    function _build(){
      local dir="$project_dir/$project_name/target"
      run_cmd "cp $dir/$project_name.war $project_dir/ROOT.war"
      build_image $tomcat_image
      #tag_image $tomcat_image
       push_image $tomcat_image
     }

    function deploy(){
        remove_container $tomcat_container_name
        pull_image $tomcat_image
        run_tomcat_container
    }

    function build_image(){
        run_cmd "docker build -f $project_dir/service/Dockerfile  -t $1 ."
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

                   function run_tomcat_container(){
                   local args="--name $tomcat_container_name"
                   args="$args -d -p $tomcat_port:8080"
                   args="$args -v $tomcat_logs_dir:/usr/local/tomcat/logs"
                   args="$args -v $tomcat_logs_dir/business_logs:$docker_log_dir"
                   args="$args $tomcat_image"
                   run_cmd "docker run $args"
               }

               function mvn(){
               local profile=$1
               local_host="`hostname --fqdn`"
                main_local_ip==`host $local_host 2>/dev/null | awk '{print $NF}'`
               args="$args -v $(pwd):/project"
               args="$args -v $maven_m2_dir:/root/.m2"
               args="$args -w /project"
               args="$args --entrypoint mvn"
               args="$args $maven_image"
               args="$args clean package -U -Ddubbo_host=$main_local_ip -Dmaven.test.skip=true -P $profile"
               run_cmd "docker run $args"
           }

           . $(pwd)/service/base.sh
