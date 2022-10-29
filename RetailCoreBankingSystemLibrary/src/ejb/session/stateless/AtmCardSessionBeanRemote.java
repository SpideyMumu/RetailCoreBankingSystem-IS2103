/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Remote;

/**
 *
 * @author muhdm
 */
@Remote
public interface AtmCardSessionBeanRemote {
    
    public Long createNewAtmCard(AtmCard newCard);
    
    public void changePin(String newPin, AtmCard currCard);

    public void updateAtmCard(AtmCard card);

    public AtmCard retrieveAtmCardByCardNumber(String cardNum);

    public AtmCard retrieveAtmCardByCardId(Long cardId);

    public void deleteAtmCard(Long atmCardId);
    
}
