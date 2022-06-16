#!/bin/bash

echo '2 threads 5m file - search flag' > fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=5m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '2 threads 10m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=10m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '2 threads 25m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=25m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '2 threads 50m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=50m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '2 threads 100m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=100m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '2 threads 200m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=2 --bs=1m --size=200m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log

echo '5 threads 5m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=5m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '5 threads 10m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=10m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '5 threads 25m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=25m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '5 threads 50m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=50m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '5 threads 100m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=100m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '5 threads 200m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=5 --bs=1m --size=200m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log

echo '10 threads 5m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=5m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '10 threads 10m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=10m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '10 threads 25m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=25m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '10 threads 50m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=50m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '10 threads 100m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=100m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '10 threads 200m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=10 --bs=1m --size=200m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log

echo '15 threads 5m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15 --bs=1m --size=5m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '15 threads 10m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15 --bs=1m --size=10m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '15 threads 25m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15 --bs=1m --size=25m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '15 threads 50m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15 --bs=1m --size=50m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '15 threads 100m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15--bs=1m --size=100m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '15 threads 200m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=15 --bs=1m --size=200m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log

echo '20 threads 5m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=5m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '20 threads 10m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=10m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '20 threads 25m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=25m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '20 threads 50m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=50m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '20 threads 100m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=100m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log
echo '20 threads 200m file - search flag' >> fio.log
fio --ioengine=libaio --iodepth=8 --readwrite=read --numjobs=20 --bs=1m --size=200m --name=job0 --filename=/home/ec2-user/efs/testfile_read_job0 --thread >> fio.log