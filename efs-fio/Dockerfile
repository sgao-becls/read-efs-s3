FROM public.ecr.aws/lambda/java:11

RUN yum install fio -y

RUN pwd ${LAMBDA_TASK_ROOT}

COPY ../target/classes ${LAMBDA_TASK_ROOT}
COPY target/lib/* ${LAMBDA_TASK_ROOT}/lib/
COPY lambda_m_threads_test.sh ${LAMBDA_TASK_ROOT}/lambda_m_threads_test.sh

RUN chmod +x ${LAMBDA_TASK_ROOT}/lambda_m_threads_test.sh

CMD ["org.cytobank.test.FioTestHandler::handleRequest"]