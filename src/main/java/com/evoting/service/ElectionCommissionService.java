package com.evoting.service;

import com.evoting.dao.CandidateDao;
import com.evoting.dao.PartyDao;
import com.evoting.dao.UserDao;
import com.evoting.model.Candidate;
import com.evoting.model.Party;
import com.evoting.model.Role;
import com.evoting.model.User;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class ElectionCommissionService {
    private CandidateDao candidateDao;
    private PartyDao partyDao;
    private UserDao userDao;

    public ElectionCommissionService() {
        this.candidateDao = new CandidateDao();
        this.partyDao = new PartyDao();
        this.userDao = new UserDao();
    }

    public void addCandidate(Candidate candidate, InputStream inputFile){

        try {
            byte[] fileBytes =  IOUtils.toByteArray(inputFile);
            candidate.setProfileImg(fileBytes);

            candidateDao.openCurrentSessionWithTransaction();
            candidateDao.save(candidate);
            candidateDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPollingStaff(User user, InputStream inputFile) {
        try {
            byte[] fileBytes = IOUtils.toByteArray(inputFile);
            user.setPhoto(fileBytes);
            userDao.openCurrentSessionWithTransaction();
            userDao.save(user);
            userDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteCandidate(int id){
        candidateDao.openCurrentSessionWithTransaction();
        Candidate candidate = candidateDao.findById(id);
        candidateDao.delete(candidate);
        candidateDao.closeCurrentSessionWithTransaction();
    }

    public void deleteAllCandidate(){
        candidateDao.openCurrentSessionWithTransaction();
        candidateDao.deleteAll();
        candidateDao.closeCurrentSessionWithTransaction();

    }

    public List<Candidate> findAllCandidate() {
        candidateDao.openCurrentSession();
        List<Candidate> candidates = candidateDao.findAll();
        candidateDao.closeCurrentSession();
        return candidates;
    }

    public Candidate findCandidateById(int id){
        candidateDao.openCurrentSessionWithTransaction();
        Candidate candidate = candidateDao.findById(id);
        candidateDao.closeCurrentSessionWithTransaction();
        return candidate;
    }

    public void updateCandidate(Candidate candidate){
        candidateDao.openCurrentSessionWithTransaction();
        candidateDao.update(candidate);
        candidateDao.closeCurrentSessionWithTransaction();
    }

    public void addParty(Party party, InputStream inputFile){

        try {
            byte[] fileBytes =  IOUtils.toByteArray(inputFile);
            party.setLogo(fileBytes);

            partyDao.openCurrentSessionWithTransaction();
            partyDao.save(party);
            partyDao.closeCurrentSessionWithTransaction();
            inputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Party> findAllParty() {
        partyDao.openCurrentSession();
        List<Party> parties = partyDao.findAll();
        partyDao.closeCurrentSession();
        return parties;
    }

    public Party findPartyById(int id){
        partyDao.openCurrentSessionWithTransaction();
        Party party = partyDao.findById(id);
        partyDao.closeCurrentSessionWithTransaction();
        return party;
    }

    public User findPollingStaffById(int id){
        userDao.openCurrentSessionWithTransaction();
        User user = userDao.findById(id);
        userDao.closeCurrentSessionWithTransaction();
        return user;
    }

    public List<User> findAll() {
        userDao.openCurrentSession();
        List<User> user = userDao.findAll();
        userDao.closeCurrentSession();
        return user;
    }

    public List<User> findAllPollingStaff(){
        List<User> pollingStaff = findAll().stream()
                .filter(e -> e.getRole().equals(Role.Polling_Staff)).collect(Collectors.toList());
        return pollingStaff;
    }
}
