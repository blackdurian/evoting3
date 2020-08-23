package com.evoting;

import static spark.Spark.port;

import com.evoting.controller.AdminController;
import com.evoting.controller.ElectionCommissionController;
import com.evoting.controller.LogInController;
import com.evoting.controller.PollingStaffController;
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
     post("/login",LogInController.handleLogoutPost);

     get("/admin/index", AdminController.getAllUser); //TODO INDEX PAGE
     get("/admin/view-user", AdminController.getAllUser);
     get("/admin/view-voter", AdminController.getUserById);
     get("/admin/img/:id/voterProfile.png",AdminController.getImageByUserId);
     get("/admin/register-voter", AdminController.serveRegisterVoter);
     post("/admin/register-voter", AdminController.registerVoter);
     post("/admin/update-voter-profile", AdminController.updateVoterById);

     get("/pollingStaff/view-voters", PollingStaffController.getAllVoter);
     get("/pollingStaff/view-voterProfile", PollingStaffController.getVoterById);
     get("/pollingStaff/img/:id/voterProfile.png",PollingStaffController.getImageByVoterId);

     get("/electionCommission/view-candidates", ElectionCommissionController.getAllCandidates);
     get("/electionCommission/view-candidateProfile", ElectionCommissionController.getCandidateById);
     get("/electionCommission/img/:id/candidateProfile.png",ElectionCommissionController.getImageByCandidateId);
     get("/electionCommission/view-pollingStaffs", ElectionCommissionController.getAllPollingStaff);
     get("/electionCommission/view-pollingStaffProfile", ElectionCommissionController.getPollingStaffById);
     get("/electionCommission/img/:id/pollingStaffProfile.png",ElectionCommissionController.getImageByPollingStaffId);
     get("/electionCommission/view-parties", ElectionCommissionController.getAllParties);
     get("/electionCommission/view-partyProfile", ElectionCommissionController.getPartyById);
     get("/electionCommission/img/:id/partyProfile.png",ElectionCommissionController.getImageByPartyId);
     get("/electionCommission/register-party", ElectionCommissionController.serveRegisterParty);
     get("/electionCommission/register-candidate", ElectionCommissionController.serveRegisterCandidate);
     get("/electionCommission/register-pollingStaff", ElectionCommissionController.serveRegisterPollingStaff);
     post("/electionCommission/register-party", ElectionCommissionController.registerParty);
     post("/electionCommission/register-candidate", ElectionCommissionController.registerCandidate);
     post("/electionCommission/register-pollingStaff", ElectionCommissionController.registerPollingStaff);






   // post("/register/",);

   // get("*",                     ViewUtil.notFound);

    //Set up after-filters (called after each get/post)
  //  after("*",                   Filters.addGzipHeader);

  }
}
