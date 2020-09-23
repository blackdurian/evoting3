package com.evoting;

import static spark.Spark.port;

import com.evoting.controller.*;
import com.evoting.service.PollingStaffService;
import com.evoting.util.Filters;

import static spark.Spark.*;

public class Application {

  public static void main(String[] args) {

    port(8080);
    staticFiles.location("/templates");


     before("/admin/*",                  Filters.handleAdminRole);
     before("/electionCommission/*",     Filters.handleElectionCommissionRole);
     before("/pollingStaff/*",           Filters.handlePollingStaffRole);
     before("/voter/*",                  Filters.handleVoterRole);

     get("/login", LogInController.serveLoginPage);
     post("/login",LogInController.handleLoginPost);
     post("/logout",LogInController.handleLogoutPost);

     get("/admin/index", AdminController.serveDoughNutDashBoard); //TODO INDEX PAGE

     get("/admin/view-user", AdminController.getAllUser);
     get("/admin/view-voter", AdminController.getUserById);
     get("/admin/img/:id/voterProfile.jpg",AdminController.getImageByUserId);
     get("/admin/register-voter", AdminController.serveRegisterVoter);
     get("/admin/states-dashboard", AdminController.serveDoughNutDashBoard);
     get("/admin/barchart-dashboard", AdminController.serveBarChartDashBoard);
     get("/api/countTotal","application/json", AdminController.serveCountTotalDashBoard);
    get("/api/voteResult","application/json", AdminController.serveVoteResult);
   get("/api/voteCount","application/json", AdminController.serveVoteCount);

     post("/admin/register-voter", AdminController.registerVoter);
     post("/admin/update-voter-profile", AdminController.updateVoterById);


     get("/pollingStaff/view-voters", PollingStaffController.getAllVoter);
     get("/pollingStaff/view-voterProfile", PollingStaffController.getVoterById);
     get("/pollingStaff/img/:id/voterProfile.jpg",PollingStaffController.getImageByVoterId);
     get("/pollingStaff/img/:id/candidate-competition.jpg",PollingStaffController.getImageByCandidateId);
     get("/pollingStaff/img/:id/party-competition.jpg", PollingStaffController.getImageByPartyId);
     get("/pollingStaff/candidate-campaign",PollingStaffController.getCandidateOptionByVoterId);
     get("/pollingStaff/states-dashboard", PollingStaffController.serveDoughNutDashBoard);
     get("/pollingStaff/barchart-dashboard", PollingStaffController.serveBarChartDashBoard);
     post("/pollingStaff/prepare-vote", PollingStaffController.prepareVote);

     get("/electionCommission/view-candidates", ElectionCommissionController.getAllCandidates);
     get("/electionCommission/view-candidateProfile", ElectionCommissionController.getCandidateById);
     get("/electionCommission/img/:id/candidateProfile.jpg",ElectionCommissionController.getImageByCandidateId);
     get("/electionCommission/view-pollingStaffs", ElectionCommissionController.getAllPollingStaff);
     get("/electionCommission/view-pollingStaffProfile", ElectionCommissionController.getPollingStaffById);
     get("/electionCommission/img/:id/pollingStaffProfile.jpg",ElectionCommissionController.getImageByPollingStaffId);
     get("/electionCommission/view-parties", ElectionCommissionController.getAllParties);
     get("/electionCommission/view-partyProfile", ElectionCommissionController.getPartyById);
     get("/electionCommission/img/:id/partyProfile.jpg",ElectionCommissionController.getImageByPartyId);
     get("/electionCommission/register-party", ElectionCommissionController.serveRegisterParty);
     get("/electionCommission/register-candidate", ElectionCommissionController.serveRegisterCandidate);
     get("/electionCommission/register-pollingStaff", ElectionCommissionController.serveRegisterPollingStaff);
     get("/electionCommission/result-counting-page", ElectionCommissionController.serveCountingPage);
     get("/electionCommission/states-dashboard", ElectionCommissionController.serveDoughNutDashBoard);
     get("/electionCommission/barchart-dashboard", ElectionCommissionController.serveBarChartDashBoard);

     post("/electionCommission/result-counting-page", ElectionCommissionController.updateResult);
     post("/electionCommission/register-party", ElectionCommissionController.registerParty);
     post("/electionCommission/register-candidate", ElectionCommissionController.registerCandidate);
     post("/electionCommission/register-pollingStaff", ElectionCommissionController.registerPollingStaff);

     get("/voter/confirm-dialog", VoterController.getConfirmation);
     get("/voter/view-status", VoterController.getStatus);
     post("/voter/vote", VoterController.handleVotingProcess);




   // post("/register/",);

   // get("*",                     ViewUtil.notFound);

    //Set up after-filters (called after each get/post)
  //  after("*",                   Filters.addGzipHeader);

  }
}
