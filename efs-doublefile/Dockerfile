FROM public.ecr.aws/lambda/java:11

COPY ./target/classes ${LAMBDA_TASK_ROOT}
COPY ./target/lib/* ${LAMBDA_TASK_ROOT}/lib/

RUN ls ${LAMBDA_TASK_ROOT}
RUN ls ${LAMBDA_TASK_ROOT}/lib/

CMD ["org.cytobank.test.FileHandler::handleRequest"]