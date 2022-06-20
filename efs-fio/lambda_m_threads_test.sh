#!/bin/bash

echo '2 threads 5m file - search flag'
fio --name=m_thread_m_file_2_5 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=5M --numjobs=2 --thread
echo '2 threads 10m file - search flag'
fio --name=m_thread_m_file_2_10 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=10M --numjobs=2 --thread
echo '2 threads 25m file - search flag'
fio --name=m_thread_m_file_2_25 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=25M --numjobs=2 --thread
echo '2 threads 50m file - search flag'
fio --name=m_thread_m_file_2_50 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=50M --numjobs=2 --thread
echo '2 threads 100m file - search flag'
fio --name=m_thread_m_file_2_100 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=100M --numjobs=2 --thread
echo '2 threads 200m file - search flag'
fio --name=m_thread_m_file_2_200 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=200M --numjobs=2 --thread

echo '5 threads 5m file - search flag'
fio --name=m_thread_m_file_5_5 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=5M --numjobs=5 --thread
echo '5 threads 10m file - search flag'
fio --name=m_thread_m_file_5_10 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=10M --numjobs=5 --thread
echo '5 threads 25m file - search flag'
fio --name=m_thread_m_file_5_25 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=25M --numjobs=5 --thread
echo '5 threads 50m file - search flag'
fio --name=m_thread_m_file_5_50 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=50M --numjobs=5 --thread
echo '5 threads 100m file - search flag'
fio --name=m_thread_m_file_5_100 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=100M --numjobs=5 --thread
echo '5 threads 200m file - search flag'
fio --name=m_thread_m_file_5_200 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=200M --numjobs=5 --thread

echo '10 threads 5m file - search flag'
fio --name=m_thread_m_file_10_5 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=5M --numjobs=10 --thread
echo '10 threads 10m file - search flag'
fio --name=m_thread_m_file_10_10 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=10M --numjobs=10 --thread
echo '10 threads 25m file - search flag'
fio --name=m_thread_m_file_10_25 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=25M --numjobs=10 --thread
echo '10 threads 50m file - search flag'
fio --name=m_thread_m_file_10_50 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=50M --numjobs=10 --thread
echo '10 threads 100m file - search flag'
fio --name=m_thread_m_file_10_100 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=100M --numjobs=10 --thread
echo '10 threads 200m file - search flag'
fio --name=m_thread_m_file_10_200 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=200M --numjobs=10 --thread

echo '15 threads 5m file - search flag'
fio --name=m_thread_m_file_15_5 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=5M --numjobs=15 --thread
echo '15 threads 10m file - search flag'
fio --name=m_thread_m_file_15_10 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=10M --numjobs=15 --thread
echo '15 threads 25m file - search flag'
fio --name=m_thread_m_file_15_25 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=25M --numjobs=15 --thread
echo '15 threads 50m file - search flag'
fio --name=m_thread_m_file_15_50 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=50M --numjobs=15 --thread
echo '15 threads 100m file - search flag'
fio --name=m_thread_m_file_15_100 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=100M --numjobs=15 --thread
echo '15 threads 200m file - search flag'
fio --name=m_thread_m_file_15_200 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=200M --numjobs=15 --thread

echo '20 threads 5m file - search flag'
fio --name=m_thread_m_file_20_5 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=5M --numjobs=20 --thread
echo '20 threads 10m file - search flag'
fio --name=m_thread_m_file_20_10 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=10M --numjobs=20 --thread
echo '20 threads 25m file - search flag'
fio --name=m_thread_m_file_20_25 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=25M --numjobs=20 --thread
echo '20 threads 50m file - search flag'
fio --name=m_thread_m_file_20_50 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=50M --numjobs=20 --thread
echo '20 threads 100m file - search flag'
fio --name=m_thread_m_file_20_100 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=100M --numjobs=20 --thread
echo '20 threads 200m file - search flag'
fio --name=m_thread_m_file_20_200 --iodepth=8 --ioengine=libaio --bs=1M --directory=/mnt/channelstripe --filesize=200M --numjobs=20 --thread
