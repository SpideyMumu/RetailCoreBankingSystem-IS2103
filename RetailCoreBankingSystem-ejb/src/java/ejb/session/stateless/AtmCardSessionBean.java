/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author muhdm
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewAtmCard(AtmCard newCard) {
        em.persist(newCard);
        em.flush();
        
        return newCard.getAtmCardId();
    }
    
    //retrieve Atm card method to be implemented
    //public AtmCard retrieveAtmCardByCardNumber(String)

    @Override
    public void changePin(String newPin, AtmCard currCard) {
        currCard.setPin(newPin);
        em.merge(currCard);
    }
    
    @Override
    public void updateAtmCard(AtmCard card) {
        em.merge(card);
    }
    
    @Override
    public AtmCard retrieveAtmCardByCardNumber(String cardNum) {
        Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :currCardNum");
        query.setParameter("currCardNum", cardNum);
        
        return (AtmCard) query.getSingleResult(); //implement try and catch exception here
    }
    
    public AtmCard retrieveAtmCardByCardId(Long cardId) {
        return em.find(AtmCard.class, cardId);
    }
    
}
