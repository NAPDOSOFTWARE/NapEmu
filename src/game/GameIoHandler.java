
package game;

/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
import Network.MinaIoHandler;
import game.Events.Ae;
import game.Events.Ce;
import org.apache.mina.core.session.IoSession;

public class GameIoHandler extends MinaIoHandler
{
  public static int SENT = 0;
  public static int RECV = 0;
  public static int CON = 0;
  
  @Override
  public void sessionCreated(IoSession session)
    throws Exception
  {
    CON += 1;
    GamePacket.HELLO_GAME.send(session);
  }
  
  @Override
  public void messageReceived(IoSession session, Object message) throws Exception
  {
    RECV += 1;
  
    String packet = ((String)message).trim();
    if (packet.length() > 1)
    {
      System.out.println("Recv << " + packet);
      if ((!packet.startsWith("AT")) && (!session.containsAttribute("account")))
      {
        session.close(true);
        return;
      }
     switch (packet.charAt(0)) {
                case 'A': //packet perso / compte
                    switch (packet.charAt(1)) {
                        case 'T': //attache account
                            Ae.onAttach(session, packet.substring(2));
                            break;
                        case 'L':
                            Ae.ListPersos(session);
                            break;
                        case 'P': //name generator
                           // Ce.onNameGenerator(session);
                            break;
                        case 'A': //character add
                            Ce.onCharacterAdd(session, packet.substring(2));
                            break;
                        case 'S': //character selected
                            Ce.onCharacterSelected(session, packet);
                            break;
                        case 'D': //delete
                          // Ce.onDelete(session, packet.substring(2));
                            break;
                    }
                    break;
      
    
  }
}
  }
}

