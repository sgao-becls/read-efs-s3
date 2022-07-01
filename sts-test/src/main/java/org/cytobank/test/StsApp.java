package org.cytobank.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class StsApp {

  public static void main(String[] args) {
    String assumeRoleArn = "arn:aws:iam::108170245965:role/song-dev-us-west-2-TokenBrokerFn-assume-role";
    String roleSessionName = "song-session";

    Regions region = Regions.US_WEST_2;
    AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard().withRegion(region).build();
    Credentials credentials = assumeGivenRole(stsClient, assumeRoleArn, roleSessionName);

    BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
        credentials.getAccessKeyId(),
        credentials.getSecretAccessKey(),
        credentials.getSessionToken());

    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
        .build();

    ObjectListing objectListing = s3Client.listObjects("song-dev-us-west-2-file-storage");
    objectListing.getObjectSummaries().forEach(s3ObjectSummary -> System.out.println(s3ObjectSummary.getKey()));
  }

  public static Credentials assumeGivenRole(AWSSecurityTokenService stsClient, String roleArn, String roleSessionName) {
      AssumeRoleRequest roleRequest = new AssumeRoleRequest()
          .withRoleArn(roleArn)
          .withRoleSessionName(roleSessionName);

      AssumeRoleResult assumeRoleResult = stsClient.assumeRole(roleRequest);
      Credentials myCreds = assumeRoleResult.getCredentials();

      // Display the time when the temp creds expire.
      Instant exTime = myCreds.getExpiration().toInstant();
      String tokenInfo = myCreds.getSessionToken();

      // Convert the Instant to readable date.
      DateTimeFormatter formatter =
          DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
              .withLocale( Locale.US)
              .withZone( ZoneId.systemDefault() );

      formatter.format( exTime );
      System.out.println("The token "+tokenInfo + " \nexpires on " + exTime );
      return myCreds;
  }
}
