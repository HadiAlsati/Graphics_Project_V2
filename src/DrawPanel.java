import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;
import java.util.ArrayList;

//There are bugs that emerge. Work on fixing those.
//Fix highlted issue
class DrawPanel extends JPanel implements MouseListener {
    private Boolean isReplacedClicked = false;
    private Rectangle replace;
    private Rectangle reset;
    private Rectangle resetCardsBeforeReplacement;
    private Deck deck;
    private Card currentCard;
    private Card [][] threeByThree = new Card[3][3];
    private Boolean areAvailableMoves = true;
    private Boolean areCardsSum11 = false;
    ArrayList<Integer> intThreeByThree;
    ArrayList<Integer> cardNums = new ArrayList<Integer>();
    int counterFaceCards = 0;
    ArrayList<Integer> faceRows = new ArrayList<>();
    ArrayList<Integer> faceCols = new ArrayList<>();
    //All you need is to make ths variable increment by 1 when a face card is clicked on, and then set to 0 when anything else is
    ArrayList<Integer> rows = new ArrayList<Integer>();
    ArrayList<Integer> cols = new ArrayList<Integer>();
    ArrayList<Card> highlighted = new ArrayList<>();

    public DrawPanel() {
        replace = new Rectangle(350, 25, 110, 30);
        reset = new Rectangle(350, 100, 110, 30);
        resetCardsBeforeReplacement = new Rectangle(350, 175, 110, 30);
        deck = new Deck();
        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c < 3; c++)
            {
                currentCard = deck.getRandomCard();
                threeByThree[r][c] = currentCard;
            }
        }
        this.addMouseListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        g.drawRect(350, 25, 110, 30);
        g.drawString("Replace Cards", 360, 45);
        g.drawRect(350, 100, 110, 30);
        g.drawString("Play Again", 373, 120);
        g.drawRect(350, 175, 110, 30);
        g.drawString("Unselect All Cards", 355, 195);
        for(int r = 0; r < 3; r++)
        {
            x = 50;
            for(int c = 0; c < 3; c++)
            {
                g.drawImage(threeByThree[r][c].getImage(), x, y, null);
                Rectangle h = new Rectangle(x, y, threeByThree[r][c].getImage().getWidth(), threeByThree[r][c].getImage().getHeight());
                threeByThree[r][c].setHitbox(h);
                if (threeByThree[r][c].getHighlight()) {
                    g.drawRect(x, y, (int)h.getWidth(), (int)h.getHeight());
                }
                x = x + 100;
            }
            y = y + 100;
        }
        g.drawString("Number of cards left: " + deck.getCards().size(), x, y + 100);
        if(!areAvailableMoves)
        {
            g.drawString("No available moves remaining.", x - 50, y + 50);
        }
        if(areAvailableMoves && deck.getCards().size() == 0)
        {
            g.drawString("You won!", x - 50, y + 50);
        }
    }

    public void mousePressed(MouseEvent e)
    {
        int counterSum11 = 0;
        intThreeByThree = new ArrayList<Integer>();
        Point p = e.getPoint();
        int button = e.getButton();
        // 1 is left click three is right click
        if(resetCardsBeforeReplacement.contains(p))
        {
            cardNums.clear();
            counterFaceCards = 0;
            if(highlighted.size() > 1)
            {
                for(int i = 0; i < highlighted.size(); i++)
                {
                    highlighted.get(i).flipHighlight();
                }
                highlighted.clear();
            }
            faceRows.clear();
            faceCols.clear();
            rows.clear();
            cols.clear();
            counterFaceCards = 0;
        }
        if(replace.contains(p))
        {
            isReplacedClicked = true;
        }
        if(reset.contains(p))
        {
            for(int r = 0; r < 3; r++)
            {
                for(int c = 0; c < 3; c++)
                {
                    threeByThree[r][c] = deck.getRandomCardNoConstraints();
                }
            }
            if(highlighted.size() > 0)
            {
                for(int i = 0; i < highlighted.size(); i++)
                {
                    highlighted.get(0).flipHighlight();
                    highlighted.remove(0);
                }
            }
            areAvailableMoves = true;
            cardNums.clear();
            counterFaceCards = 0;
            faceCols.clear();
            faceRows.clear();
        }
        for(int r = 0; r < threeByThree.length; r++)
        {
            for(int c = 0; c < threeByThree.length; c++)
            {
                if(deck.getCards().size() != 0 && threeByThree[r][c].getHitbox().contains(p) && button == 1)
                {
                    if(threeByThree[r][c].getValue().equals("J"))
                    {
                        counterFaceCards++;
                        faceRows.add(r);
                        faceCols.add(c);
                    }
                    if(threeByThree[r][c].getValue().equals("Q"))
                    {
                        counterFaceCards++;
                        faceRows.add(r);
                        faceCols.add(c);
                    }
                    if(threeByThree[r][c].getValue().equals("K"))
                    {
                        counterFaceCards++;
                        faceRows.add(r);
                        faceCols.add(c);
                    }
                    if(threeByThree[r][c].getValue().equals("A"))
                    {
                        cardNums.add(1);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("02"))
                    {
                        cardNums.add(2);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("03"))
                    {
                        cardNums.add(3);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("04"))
                    {
                        cardNums.add(4);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        for(int i = 0; i < x; i++)
                        {
                            faceRows.remove(0);
                            faceCols.remove(0);
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("05"))
                    {
                        cardNums.add(5);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("06"))
                    {
                        cardNums.add(6);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("07"))
                    {
                        cardNums.add(7);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("08"))
                    {
                        cardNums.add(8);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("09"))
                    {
                        cardNums.add(9);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                    if(threeByThree[r][c].getValue().equals("10"))
                    {
                        cardNums.add(10);
                        rows.add(r);
                        cols.add(c);
                        counterFaceCards = 0;
                        int x = faceRows.size();
                        if(x > 0)
                        {
                            for(int i = 0; i < x; i++)
                            {
                                faceRows.remove(0);
                                faceCols.remove(0);
                            }
                        }
                    }
                }
                if(deck.getCards().size() != 0 && threeByThree[r][c].getHitbox().contains(p) && button == 1)
                {
                    threeByThree[r][c].flipHighlight();
                    highlighted.add(threeByThree[r][c]);
                }
            }
        }
//        System.out.println("cardnums" + cardNums);
//        System.out.println(highlighted);
        if(cardNums.size() == 2)
        {
            if(cardNums.get(0) + cardNums.get(1) == 11 && isReplacedClicked)
            {
                threeByThree[rows.get(0)][cols.get(0)] = deck.getRandomCard();
                System.out.println(threeByThree[rows.get(0)][cols.get(0)]);
                threeByThree[rows.get(1)][cols.get(1)] = deck.getRandomCard();
                System.out.println(threeByThree[rows.get(1)][cols.get(1)]);
                rows.remove(0);
                rows.remove(0);
                cols.remove(0);
                cols.remove(0);
                cardNums.remove(0);
                cardNums.remove(0);
                isReplacedClicked = false;
//                highlighted.get(0).flipHighlight();
//                highlighted.get(1).flipHighlight();
                highlighted.clear();
            }
        }
        else if(cardNums.size() > 2)
        {
            int  y = cardNums.size();
            for(int i = 0; i < y; i++)
            {
                cardNums.remove(0);
                rows.remove(0);
                cols.remove(0);
            }
        }
        if(counterFaceCards == 3 && isReplacedClicked)
        {
            threeByThree[faceRows.get(0)][faceCols.get(0)] = deck.getRandomCard();
            threeByThree[faceRows.get(1)][faceCols.get(1)] = deck.getRandomCard();
            threeByThree[faceRows.get(2)][faceCols.get(2)] = deck.getRandomCard();
            faceRows.remove(0);
            faceRows.remove(0);
            faceRows.remove(0);
            faceCols.remove(0);
            faceCols.remove(0);
            faceCols.remove(0);
            isReplacedClicked = false;
        }
        else if(isReplacedClicked)
        {
            isReplacedClicked = false;
            cardNums.clear();
            faceRows.clear();
            for(int i = 0; i < highlighted.size() + 1; i++)
            {
                highlighted.get(0).flipHighlight();
                highlighted.remove(0);
            }
        }

        int numJackKingQueen = 0;
        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c < 3; c++)
            {
                if(threeByThree[r][c].getValue().equals("J") || threeByThree[r][c].getValue().equals("K") || threeByThree[r][c].getValue().equals("Q"))
                {
                    numJackKingQueen++;
                }
                if(threeByThree[r][c].getValue().equals("A"))
                {
                    intThreeByThree.add(1);
                }
                if(threeByThree[r][c].getValue().equals("02"))
                {
                    intThreeByThree.add(2);
                }
                if(threeByThree[r][c].getValue().equals("03"))
                {
                    intThreeByThree.add(3);
                }
                if(threeByThree[r][c].getValue().equals("04"))
                {
                    intThreeByThree.add(4);
                }
                if(threeByThree[r][c].getValue().equals("05"))
                {
                    intThreeByThree.add(5);
                }
                if(threeByThree[r][c].getValue().equals("06"))
                {
                    intThreeByThree.add(6);
                }
                if(threeByThree[r][c].getValue().equals("07"))
                {
                    intThreeByThree.add(7);
                }
                if(threeByThree[r][c].getValue().equals("08"))
                {
                    intThreeByThree.add(8);
                }
                if(threeByThree[r][c].getValue().equals("09"))
                {
                    intThreeByThree.add(9);
                }
                if(threeByThree[r][c].getValue().equals("10"))
                {
                    intThreeByThree.add(10);
                }
            }
        }
        for(int x = 0; x < intThreeByThree.size(); x++)
        {
            for(int y = x + 1; y < intThreeByThree.size(); y++)
            {
                if(intThreeByThree.get(x) + intThreeByThree.get(y) == 11)
                {
                    counterSum11++;
                }
            }
        }
        if(counterSum11 > 0)
        {
            areCardsSum11 = true;
        }
        else
        {
            areCardsSum11 = false;
        }
        if(numJackKingQueen < 3 && !areCardsSum11)
        {
            areAvailableMoves = false;
        }
    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}