#!/usr/bin/env bash

for (( i=1; i<11; i++))
do
  nuhup java -jar read-efs-s3.jar > log$i.log &
done
echo 'done!'
