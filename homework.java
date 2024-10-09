import java.io.*;
import java.util.*;
import static java.util.stream.Collectors.toMap;

class homework
{  
	 public static void main(String[] args)throws Exception
	    {        
	       long startTime = System.currentTimeMillis();
	       
	       File file  = new File ("input.txt");
	       BufferedReader buff = new BufferedReader(new FileReader(file));
	       String typeOfGame = buff.readLine();
	       String colour = buff.readLine();
	       
	       float timeLimit = Float.parseFloat(buff.readLine());
	       timeLimit =timeLimit*1000;
	       char[][] board = new char[16][16];
	       String eachLine;
	       for (int i=0;i<16;i++)
	       {
	           eachLine = buff.readLine();
	           for (int j=0;j<16;j++)
	           {
	                board[i][j] = eachLine.charAt(j);               
	           }          
	       }     
	      
	       switch(typeOfGame)
	       {
	       case "SINGLE":
	           SingleImplementation( board,colour, timeLimit, startTime);     
	           break;
	       case "GAME":
	    	   GameImplementation( board,colour, timeLimit, startTime);   
	       }
	       buff.close();
	}    
	 
	    public static ArrayList<String> baseCoordinates(String colour)
	    {
	        ArrayList<String> base = new ArrayList<>();
	        if (colour.equals ("WHITE"))
	             {
	               base.add ("11,14");
	               base.add ("11,15");
	               base.add ("12,13");
	               base.add ("12,14");
	               base.add ("12,15");
	               base.add ("13,12");
	               base.add ("13,13");
	               base.add ("13,14");
	               base.add ("13,15");
	               base.add ("14,11");
	               base.add ("14,12");
	               base.add ("14,13");
	               base.add ("14,14");
	               base.add ("14,15");
	               base.add ("15,11");
	               base.add ("15,12");
	               base.add ("15,13");
	               base.add ("15,14");
	               base.add ("15,15");    
	             }
	        else
	            {
	                
	               base.add ("4,0");
	               base.add ("4,1");   
	               base.add ("3,0");
	               base.add ("3,1");
	               base.add ("3,2");
	               base.add ("2,0");
	               base.add ("2,1");
	               base.add ("2,2");
	               base.add ("2,3");
	               base.add ("1,0");
	               base.add ("1,1");
	               base.add ("1,2");
	               base.add ("1,3");
	               base.add ("1,4");
	               base.add ("0,0");
	               base.add ("0,1");
	               base.add ("0,2");
	               base.add ("0,3");
	               base.add ("0,4");
	                         
	            }     
	        return base;
	    }

	 
	 public static HashMap<String, Integer> baseHmap(String colour)
	    {
	       HashMap<String, Integer> baseHmap = new HashMap<String ,Integer>();
	        if (colour.equals ("WHITE"))
	             {
	             	baseHmap.put ("11,14",1);
	                baseHmap.put ("11,15",1);
	                baseHmap.put ("12,13",1);
	                baseHmap.put ("12,14",1);   
	                baseHmap.put ("12,15",1);
	                baseHmap.put ("13,12",1);
	                baseHmap.put ("13,13",1);
	                baseHmap.put ("13,14",1);
	                baseHmap.put ("13,15",1);
	                baseHmap.put ("14,11",1);
	                baseHmap.put ("14,12",1);
	                baseHmap.put ("14,13",1);
	                baseHmap.put ("14,14",1);
	                baseHmap.put ("14,15",1);
	                baseHmap.put ("15,11",1);
	                baseHmap.put ("15,12",1);
	                baseHmap.put ("15,13",1);
	                baseHmap.put ("15,14",1);           
	                baseHmap.put ("15,15",1);     
	     
	             }
	        else
	            {
	                 baseHmap.put ("4,0",1);
	                 baseHmap.put ("4,1",1);     
	                 baseHmap.put ("3,0",1);
	                 baseHmap.put ("3,1",1);
	                 baseHmap.put ("3,2",1);
	                 baseHmap.put ("2,0",1);
	                 baseHmap.put ("2,1",1);
	                 baseHmap.put ("2,2",1);
	                 baseHmap.put ("2,3",1);
	                 baseHmap.put ("1,0",1);
	                 baseHmap.put ("1,1",1);
	                 baseHmap.put ("1,2",1);
		             baseHmap.put ("1,3",1);
		             baseHmap.put ("1,4",1);
		             baseHmap.put ("0,0",1);
		             baseHmap.put ("0,1",1);
		             baseHmap.put ("0,2",1);
		             baseHmap.put ("0,3",1);
		             baseHmap.put ("0,4",1); 
	            }        
	        return baseHmap;
	    }
	   
	 
	 public static void SingleImplementation (char[][] board,String colour, float timeLimit, long startTime)throws Exception
	    {       
	         ArrayList<String> removeBaseFirstMoves = removeBaseFirst( board,colour, timeLimit, startTime);
	         
	        if (removeBaseFirstMoves.size() ==0)
	        {
	           ArrayList<String> availableMoves = getAllPossibleMoves(colour, timeLimit, board, startTime);            
	           File file  = new File ("output.txt");
	           BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	           String validMove = availableMoves.get(0);
	           writer.write(validMove);
	           writer.close();
	           return;                   
	        }     
	        else
	        {
	           File file  = new File ("output.txt");
	           BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	           String validMove = removeBaseFirstMoves.get(0);
	           writer.write(validMove);
	           writer.close();
	           return;           
	        }  
	    } 
	    
	    public static void GameImplementation(char[][] board, String colour, float timeLimit, long startTime)throws Exception
	    {   
	        ArrayList<String> removeBaseFirstMoves = removeBaseFirst( board,colour, timeLimit, startTime);     
	        if (removeBaseFirstMoves.size() ==0)
	        {
	            double alpha = Double.MIN_VALUE;
	            double beta = Double.MAX_VALUE;
	 
	            String opponentColour = "";
	            if (colour.equals ("WHITE"))
	                opponentColour ="BLACK";
	            else
	                opponentColour = "WHITE";
	            
	            int count= 0;
	            
	            ArrayList<String> goalbase = baseCoordinates(opponentColour);
	            for (int i=0;i<goalbase.size();i++)
	            {
	                String[] splitBaseLine = goalbase.get(i).split(",");
	                int xCoord = Integer.parseInt(splitBaseLine[0]);
	                int yCoord = Integer.parseInt(splitBaseLine[1]);
	                if (board[xCoord][yCoord] == colour.charAt(0))
	                {
	                    count++;  
	                }
	            }
	          
	           int depth = 4;
	           timeLimit = (float)timeLimit /(65+ count);
	           String bestMove = "";
	           bestMove = minimax( board, depth, colour, timeLimit, alpha, beta, startTime, colour);
	           
	           String[] lineSplit = bestMove.split("\n");
	           String[] spaceSplit = lineSplit[lineSplit.length-1].split("\\s+");
	           
		           
		          bestMove = "";
		          for (int i =0;i< lineSplit.length-1;i++)
		              bestMove +=lineSplit[i]+"\n";
		          
		          for (int i=0;i<spaceSplit.length-1;i++)
		              bestMove+=spaceSplit[i]+" ";
		      
	          File file = new File("output.txt");
	          BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	          writer.write(bestMove);
	          writer.close();        
	        }
	        else
	        {
	            File file = new File ("output.txt");
	            BufferedWriter writer = new BufferedWriter (new FileWriter(file));
	            ArrayList<String> availableMoves = sortStart(board,removeBaseFirstMoves, colour);
	            String bestMove = availableMoves.get(0);
	            writer.write(bestMove);
	            writer.close();   
	            return;
	        } 
	    }  

    public static String findWinner (char[][] board)
    {
        ArrayList<String> base = baseCoordinates("BLACK");
       int counter =0;
        for (int i =0;i<base.size();i++)
        {
            String singleBaseCoord = base.get(i);
            String[] splitBaseLine = singleBaseCoord.split(",");
            int x = Integer.parseInt(splitBaseLine[0]);
            int y = Integer.parseInt(splitBaseLine[1]);
            if (board[x][y] =='W')
                counter++;
            else
            {
                counter =0;
                break;
            }       
        }
        if (counter ==19)
            return "WHITE";
        
      base = baseCoordinates("WHITE"); 
           for (int i =0;i<base.size();i++)
        {
            String singleBaseCoord = base.get(i);
            String[] splitBaseLine = singleBaseCoord.split(",");
            int x = Integer.parseInt(splitBaseLine[0]);
            int y = Integer.parseInt(splitBaseLine[1]);
            if (board[x][y] =='B')
                counter++;
            else
            {
                counter =0;
                break;
            }       
        }  
        if(counter ==19 )
            return "BLACK";
        else
            return "no";
    }
    
    
    
    public static String minimax (char[][] board, int depth, String currentPlayerColour, float timeLimit, double alpha, double beta, long startTime, String myPlayerColour)throws Exception
    {
       long currentTime = System.currentTimeMillis();   
        if (depth == 0)
        {
             return " "+utility(currentPlayerColour, board);
        }
        
        if ( depth == 0 || currentTime - startTime >= timeLimit -500 || !findWinner(board).equals("no"))
        {
           return " "+utility(currentPlayerColour, board);
        }
        String bestMove ="";
        double bestval;
        if (currentPlayerColour.equals (myPlayerColour))
            bestval = Double.MIN_VALUE; 
        else 
            bestval = Double.MAX_VALUE;       
            
        String opponentColour;
        if (currentPlayerColour.equals ("WHITE"))
            opponentColour = "BLACK";
        else
            opponentColour = "WHITE";
        
        File file = new File ("playdata.txt");
        
        ArrayList<String> allPossibleMoves = getAllPossibleMoves(currentPlayerColour, timeLimit, board, startTime);
        allPossibleMoves = moveSorting(allPossibleMoves, board, currentPlayerColour);
        bestMove = allPossibleMoves.get(0);       
        for (int i =0;i<allPossibleMoves.size();i++)
        {
           String move = allPossibleMoves.get(i);        
           currentTime = System.currentTimeMillis();
           if (currentTime - startTime >= timeLimit-500)
               return bestMove+" "+bestval; 
           
           makeMove(board,move);  
           
           String value = "";
           for (int row = 0;row< 16;row++)
           {
               for (int col =0;col<16;col++)
               {
                  value += String.valueOf(board[row][col])+ String.valueOf(row)+","+String.valueOf(col)+"|";
               }   
           }      
     int flagval = 0;
     double tempval =0;
          if (file.exists())
          {
           BufferedReader buff = new BufferedReader(new FileReader(file));
           String baseLine;              
           while ((baseLine =buff.readLine()) != null)
           {
               String[] splitBaseLine = baseLine.split("\\s+");
               if (splitBaseLine[0].equals (value))
               {
                   flagval=1;
                   tempval = Double.parseDouble (splitBaseLine[1]);
                   break;
               }
           }
          buff.close();
          }
           String resultOfminimax ="";
           String[] lineSplit, spaceString;
           double val;
           if (flagval ==0)
           {
                 resultOfminimax =  minimax(board, depth-1, opponentColour, timeLimit, alpha, beta, startTime, myPlayerColour);        
                  lineSplit = resultOfminimax.split("\n");
                  spaceString = lineSplit[lineSplit.length-1].split("\\s+");
                  val = Double.parseDouble(spaceString[spaceString.length-1]); 
                  BufferedWriter writer = new BufferedWriter (new FileWriter(file, true));
                  writer.append(value+" "+val+"\n");
                  writer.close();
           }
           else
               val = tempval;
           
           retractMove(move, board);  
           if (currentPlayerColour.equals(myPlayerColour) && val > bestval)
           {
               bestval = val;
               bestMove = move;
               if (val > alpha)
                   alpha = val;               
           }
           
           if (!currentPlayerColour.equals (myPlayerColour) &&  val < bestval)
           {
               bestval = val;
               bestMove = move;
               if (val< beta)
                   beta = val;
           }
           
           if (beta <= alpha)
           {
            return bestMove+" "+bestval;      
           }
           
        }
     return bestMove+" "+bestval;
    }
   
    public static float distance (float fromx, float fromy, float tox, float toy)
    {
        return (float)Math.sqrt(Math.pow(fromy -toy,2)+ Math.pow(fromx - tox,2));        
    }
  
    
    public static ArrayList<String> sortStart (char[][] board, ArrayList<String> availableMoves, String colour)throws Exception
    {
    	HashMap<String, Float> utilityHash = new HashMap<String , Float>();
    	ArrayList<String> sortedMoves;
        for (int i =0;i<availableMoves.size();i++)
        {
            String move = availableMoves.get(i);
            makeMove(board,move);           
            float utilityForThisMove = utilityInitial(colour, board);
            utilityHash.put(move, utilityForThisMove);
            retractMove(move, board);     
        }         
       Map<String, Float> sortDescending = utilityHash.entrySet()
                                                                .stream()
                                                                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                                                                .collect(toMap(Map.Entry::getKey, 
                                                                         Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
               
        sortedMoves = new ArrayList<String>(sortDescending.keySet());
        return sortedMoves;       
    }
      
   
    public static float utilityInitial (String colour, char[][] board)
    {
       float utilityval = 0;
       String oppoColor;
       if (colour.equals ("WHITE"))
           oppoColor = "BLACK";
       else 
           oppoColor = "WHITE";
       
      ArrayList<String> homebase =  baseCoordinates(colour);
      int c = 0;
        String[] splitBaseLine;
        
      for (int i =0;i< homebase.size();i++)
      {
          String inp = homebase.get(i);
          splitBaseLine = inp.split(",");
          int x = Integer.parseInt(splitBaseLine[0]);
          int y = Integer.parseInt(splitBaseLine[1]);
          if (board[x][y]!=colour.charAt(0))
              c=c+1;
      }
        
         ArrayList<String> allPawnCoordinates = allThebase( board,colour);
        float centerX =0, centerY =0 ;
    
        ArrayList<String> goalbase = baseCoordinates(oppoColor);
  
      float max =-1;
      for (int dup = 0; dup <homebase.size();dup++)
       {
           splitBaseLine = homebase.get(dup).split(",");
           int row = Integer.parseInt(splitBaseLine[0]);
           int col = Integer.parseInt(splitBaseLine[1]);
           if (board[row][col] == colour.charAt(0))
              {
                 for (int index =0; index < goalbase.size();index++)
                 {
                     splitBaseLine = goalbase.get(index).split(",");
                     int finalx = Integer.parseInt(splitBaseLine[0]);
                     int finaly = Integer.parseInt(splitBaseLine[1]);
                     if (board[finalx][finaly] != colour.charAt(0))
                     {
                        float dist = distance(row,col, finalx, finaly);
                        if (dist > max)
                             max = dist;                   
                     }
                 }
                utilityval -= max* 50;
              }   
           }            
       return utilityval;
    }
    
    
    public static float utility (String colour, char[][] board)throws Exception
    {   
       float utilityval = 0;
       String oppoColor = "";
       char playerPawn;
       if (colour.equals("WHITE"))
       {
            oppoColor = "BLACK";
            playerPawn = 'W';
       }  
       else
       {
           oppoColor = "WHITE";
           playerPawn = 'B';
       }

       ArrayList<String> goalbase = baseCoordinates(oppoColor);
       HashMap<String, Integer> goalbaseHmap = baseHmap(oppoColor);
       ArrayList<String> homebase = baseCoordinates(colour);
       
        float max = -1;
        String[] splitBaseLine;
        float centerX =0 , centerY =0;
        
       for (int row = 0; row < 16;row++)
       {
           for (int col = 0;col < 16;col ++)
           {
              if (board[row][col] == playerPawn && !goalbaseHmap.containsKey(String.valueOf(row)+","+String.valueOf(col)) )                     // Modifying this line
              {
                  centerX += row;
                  centerY +=col;
                for (int index =0; index < goalbase.size();index++)
                 {
                     splitBaseLine = goalbase.get(index).split(",");
                     int finalx = Integer.parseInt(splitBaseLine[0]);
                     int finaly = Integer.parseInt(splitBaseLine[1]);
                     if (board[finalx][finaly] != playerPawn)
                     {
                        float dist = distance(row,col, finalx, finaly);
                        if (dist > max)
                             max = dist;                   
                     }
                 }
                utilityval -= max* 50;
              }
            
           }           
       }
    
        float opponentutilityval =0;
        if (playerPawn =='W')
            playerPawn = 'B';
        else
           playerPawn = 'W';
        
        if (oppoColor.equals ("WHITE"))
            oppoColor = "BLACK";
        else
            oppoColor = "WHITE";
        
       if (colour.equals("WHITE"))
           colour = "BLACK";
       else
           colour = "WHITE";
        
        goalbase = baseCoordinates(oppoColor);
        goalbaseHmap = baseHmap(oppoColor);
        homebase = baseCoordinates(colour);
        max = -1;
        centerX =0 ; centerY =0;
       
        for (int row = 0; row < 16;row++)
        {
           for (int col = 0;col < 16;col ++)
           {
              if (board[row][col] == playerPawn && !goalbaseHmap.containsKey(String.valueOf(row)+","+String.valueOf(col)))
              {
                  centerX += row;
                  centerY +=col;
                for (int index =0; index < goalbase.size();index++)
                 {
                     splitBaseLine = goalbase.get(index).split(",");
                     int finalx = Integer.parseInt(splitBaseLine[0]);
                     int finaly = Integer.parseInt(splitBaseLine[1]);
                     if (board[finalx][finaly] != playerPawn)
                     {
                        float dist = distance(row,col, finalx, finaly);
                        if (dist > max)
                             max = dist;                   
                     }
                 }
                opponentutilityval -= max* 50;
              }
           }           
        }
        
        return utilityval - opponentutilityval;                 
    }
        
    public static ArrayList<String> allThebase(char[][] board,String colour)
    {
        ArrayList<String> base =  new ArrayList<String>();
        char playerSearch =  colour.charAt(0);
        for (int row =0;row<16;row++)
        {
           for (int column = 0;column< 16;column++)
            {
              if (board[row][column] ==playerSearch)
              {
                  base.add (String.valueOf(row)+","+String.valueOf(column));  
              }
            }            
        }  
        return base;    
    }
    
    public static int distFromBase(int x,int y, String colour)
    {
        if (colour.equals("WHITE"))
            return Math.abs (x -15)+ Math.abs(y - 15);        
        else
            return Math.abs(x) + Math.abs(y);  
    
    }
    
    public static ArrayList<String> jumpPriorities( char[][] board,String colour, int x, int y, ArrayList<String> moves, int origX, int origY, String parentString, HashMap<String,Integer> visited, HashMap<String, Integer> baseIn, long startTime, long timeLimit) 
    {
        visited.put (String.valueOf(x)+","+String.valueOf(y),1); 
       
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-1)
            return moves;
        
        if (colour.equals("WHITE"))

        {
        //north west
        if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
           if (distFromBase(x-2, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y-2))))
              moves.add (parentString);
         parentString +="\n";
         jumpPriorities( board,colour, x-2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
         currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
         
        }
     //north
        if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y] !='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
           if (distFromBase(x-2, y, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y))))
                moves.add (parentString);
           parentString +="\n";
           jumpPriorities( board,colour, x-2, y, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
           if (currentTime - startTime >= timeLimit-0.5)
             return moves;  
        }     
     //west
        if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
           if (distFromBase(x, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y-2))))
              moves.add (parentString);
                  parentString +="\n";
                  jumpPriorities( board,colour, x, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        
        }
 

       //south
		 if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
		 {
		    parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
		      if (distFromBase(x+2, y, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y))))
		     moves.add (parentString);
		          parentString +="\n";
		          jumpPriorities( board,colour, x+2, y, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
		    currentTime = System.currentTimeMillis();
		 if (currentTime - startTime >= timeLimit-0.5)
		     return moves;
		 }
		//north east
       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
          if (distFromBase(x-2, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y+2))))
          moves.add (parentString);
              parentString +="\n";
              jumpPriorities( board,colour, x-2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
        currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        }
       //east
        if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
              if (distFromBase(x, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y+2))))
            moves.add (parentString);
              parentString +="\n";
              jumpPriorities( board,colour, x, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
            currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        }
        //south west
        if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
          {
                parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
           if (distFromBase(x+2, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y-2))))
                   moves.add (parentString);
                       parentString +="\n";
                       jumpPriorities( board,colour, x+2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
                currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
          }
        //south east
        if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
            if (distFromBase(x+2, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y+2))))
                moves.add (parentString);
            parentString +="\n";
            jumpPriorities( board,colour,x+2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
           if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        }
           
        }
        
        
        else
        {   
        	//north
            if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y] !='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
            {
               parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
               if (distFromBase(x-2, y, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y))))
                    moves.add (parentString);
               parentString +="\n";
               jumpPriorities( board,colour, x-2, y, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
               currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                 return moves;  
            }     
            //south east
	         if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
	        {
	            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
	            if (distFromBase(x+2, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y+2))))
	                moves.add (parentString);
	            parentString +="\n";
	            jumpPriorities( board,colour, x+2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
	           currentTime = System.currentTimeMillis();
	           if (currentTime - startTime >= timeLimit-0.5)
	            return moves;
	        }
         //south  
        if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
             if (distFromBase(x+2, y, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y))))
            moves.add (parentString);
                 parentString +="\n";
                 jumpPriorities( board,colour, x+2, y, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
           currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        }   
        // east
        if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
              if (distFromBase(x, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y+2))))
            moves.add (parentString);
              parentString +="\n";
              jumpPriorities( board,colour, x, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
            currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        }  
        //south west
        if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
          {
                parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
           if (distFromBase(x+2, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x+2, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y-2))))
                   moves.add (parentString);
                       parentString +="\n";
                       jumpPriorities( board,colour, x+2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
                currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
          }
        //west
        if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
           if (distFromBase(x, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y-2))))
              moves.add (parentString);
                  parentString +="\n";
                  jumpPriorities( board,colour, x, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        
        }
        //north west
	      if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
	      {
	         parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
	         if (distFromBase(x-2, y-2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y-2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y-2))))
	            moves.add (parentString);
	       parentString +="\n";
	       jumpPriorities( board,colour, x-2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
	       currentTime = System.currentTimeMillis();
	      if (currentTime - startTime >= timeLimit-0.5)
	          return moves;
	       
	      }
	       //north east
	       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
	        {
	           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
	          if (distFromBase(x-2, y+2, colour) > distFromBase(origX, origY, colour) || (distFromBase(x-2, y+2, colour) == distFromBase(origX, origY, colour) && !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y+2))))
	          moves.add (parentString);
	              parentString +="\n";
	              jumpPriorities( board,colour, x-2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
	        currentTime = System.currentTimeMillis();
	        if (currentTime - startTime >= timeLimit-0.5)
	            return moves;
	        }
	        
	     }
      return moves;  
    }
    
    public static ArrayList<String> opponentBaseMoves(String colour, char[][] board, int x, int y, ArrayList<String> moves, int origX, int origY, String parentString, HashMap<String, Integer> visited, long startTime, float timeLimit, HashMap<String, Integer> goalbase)
    {
       visited.put (String.valueOf(x)+","+String.valueOf(y),1);
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        
       if (colour.equals("WHITE"))
       {
        //north west
        if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
           if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y-2)))
               moves.add (parentString);
           
           
        parentString +="\n";
        opponentBaseMoves(colour, board, x-2, y-2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
        currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;     
        }
    //west
        if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
           if (goalbase.containsKey(String.valueOf(x)+","+ String.valueOf(y-2)))
              moves.add (parentString);
                  parentString +="\n";
           opponentBaseMoves(colour, board, x, y-2, moves,origX,origY, parentString,visited,startTime, timeLimit, goalbase);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;     
        }
      //north
        if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
        {
          parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
           if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y)))
               moves.add (parentString);
          parentString +="\n";
          
          opponentBaseMoves(colour, board, x-2, y, moves,origX,origY, parentString,visited,startTime,timeLimit, goalbase);
          currentTime = System.currentTimeMillis();
          if (currentTime - startTime >= timeLimit-0.5)
            return moves;      
        }     
        //north east
       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
           if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y+2)))
          moves.add (parentString);
              parentString +="\n";
          opponentBaseMoves(colour, board, x-2, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
       //east
        if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
           if (goalbase.containsKey(String.valueOf(x)+","+ String.valueOf(y+2)))
       
           moves.add (parentString);
              parentString +="\n";             
           opponentBaseMoves(colour, board, x, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
               currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves; 
        }
       //south east
		  if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
		  {
		     parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
		     if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y+2)))
		
		       moves.add (parentString);
		          parentString +="\n";
		     opponentBaseMoves(colour, board, x+2, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
		     currentTime = System.currentTimeMillis();
		         if (currentTime - startTime >= timeLimit-0.5)
		             return moves;
		  }
        //south
        if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
           if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y)))
           moves.add (parentString);
                 parentString +="\n";
           opponentBaseMoves(colour, board, x+2, y, moves,origX,origY, parentString,visited, startTime,timeLimit, goalbase);    
                currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
        //south west
        if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
          {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
           if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y-2)))

                moves.add (parentString);
                 parentString +="\n";
                opponentBaseMoves(colour, board, x+2, y-2, moves,origX,origY, parentString,visited,  startTime, timeLimit, goalbase);
        currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
          }
       }
   
       
       else
       {
    	   //north
           if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
          {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
             if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y)))
                 moves.add (parentString);
            parentString +="\n";
            
            opponentBaseMoves(colour, board, x-2, y, moves,origX,origY, parentString,visited,startTime,timeLimit, goalbase);
            currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= timeLimit-0.5)
              return moves;      
            
          }
          //east
           if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
           {
              parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
              if (goalbase.containsKey(String.valueOf(x)+","+ String.valueOf(y+2)))
          
              moves.add (parentString);
                 parentString +="\n";             
              opponentBaseMoves(colour, board, x, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
                  currentTime = System.currentTimeMillis();
                  if (currentTime - startTime >= timeLimit-0.5)
                      return moves; 
           }    
           //south
			if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
			{
			   parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
			   if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y)))
			   moves.add (parentString);
			         parentString +="\n";
			   opponentBaseMoves(colour, board, x+2, y, moves,origX,origY, parentString,visited, startTime,timeLimit, goalbase);    
			        currentTime = System.currentTimeMillis();
			       if (currentTime - startTime >= timeLimit-0.5)
			           return moves;
			}
          //south east
	        if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
	        {
	           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
	           if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y+2)))
	
	             moves.add (parentString);
	                parentString +="\n";
	           opponentBaseMoves(colour, board, x+2, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
	           currentTime = System.currentTimeMillis();
	               if (currentTime - startTime >= timeLimit-0.5)
	                   return moves;
	        }
           //south west
		       if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
	          {
	           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
	           if (goalbase.containsKey(String.valueOf(x+2)+","+ String.valueOf(y-2)))
	
	                moves.add (parentString);
	                 parentString +="\n";
	                opponentBaseMoves(colour, board, x+2, y-2, moves,origX,origY, parentString,visited,  startTime, timeLimit, goalbase);
	        currentTime = System.currentTimeMillis();
	               if (currentTime - startTime >= timeLimit-0.5)
	                   return moves;
	          }
        //west
        if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
           if (goalbase.containsKey(String.valueOf(x)+","+ String.valueOf(y-2)))
              moves.add (parentString);
                  parentString +="\n";
           opponentBaseMoves(colour, board, x, y-2, moves,origX,origY, parentString,visited,startTime, timeLimit, goalbase);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;     
        }
      
       //north east
       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
           if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y+2)))
          moves.add (parentString);
              parentString +="\n";
          opponentBaseMoves(colour, board, x-2, y+2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
       //north west
        if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
           if (goalbase.containsKey(String.valueOf(x-2)+","+ String.valueOf(y-2)))
               moves.add (parentString);
           
           
        parentString +="\n";
        opponentBaseMoves(colour, board, x-2, y-2, moves,origX,origY, parentString,visited, startTime, timeLimit, goalbase);
        currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;     
        }
                             
       }
              
      return moves;          
    } 
  
  public static ArrayList<String> jumpMovesOutsideHome(String colour, char[][] board, int x, int y, ArrayList<String> moves, int origX, int origY, String parentString, HashMap<String,Integer> visited, HashMap<String, Integer> baseIn, long startTime, float timeLimit) 
    {    
        visited.put (String.valueOf(x)+","+String.valueOf(y),1);
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= timeLimit-0.5)
            return moves;
        
        if (colour.equals ("WHITE"))
        { 
        	// north
            if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
            {
              parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
              if ( !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y)))
                    moves.add (parentString);
              parentString +="\n";
              jumpMovesOutsideHome(colour, board, x-2, y, moves,origX,origY, parentString,visited, baseIn, startTime,timeLimit);
              currentTime = System.currentTimeMillis();
              if (currentTime - startTime >= timeLimit-0.5)
                return moves; 
            }
            //east
             if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
             {
                parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
                   if (!baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y+2)))
                 	  moves.add (parentString);
                parentString +="\n";             
                jumpMovesOutsideHome(colour, board, x, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
                currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >= timeLimit-0.5)
                        return moves;
             }
            // west
            if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
            {
               parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
               if (!baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y-2)))
                  moves.add (parentString);
               parentString +="\n";
               jumpMovesOutsideHome(colour, board, x, y-2, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
               currentTime = System.currentTimeMillis();
                   if (currentTime - startTime >= timeLimit-0.5)
                       return moves;
            }
         //south
            if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
            {
               parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
               if ( !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y)))
            	   moves.add (parentString);
               parentString +="\n";
               jumpMovesOutsideHome(colour, board, x+2, y, moves,origX,origY, parentString,visited, baseIn, startTime,timeLimit);    
               currentTime = System.currentTimeMillis();
                   if (currentTime - startTime >= timeLimit-0.5)
                       return moves;
            }
            
	        //north west
	        if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
	        {
	            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
	            if ( !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y-2)))
	                moves.add (parentString);
	        parentString +="\n";
	        jumpMovesOutsideHome(colour, board, x-2, y-2, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
	        currentTime = System.currentTimeMillis();
	               if (currentTime - startTime >= timeLimit-0.5)
	                   return moves;
	        }
	        //north east
	       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
	        {
	           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
	          if (!baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y+2)))
	        	  moves.add (parentString);
	           parentString +="\n";
	           jumpMovesOutsideHome(colour, board, x-2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
	           currentTime = System.currentTimeMillis();
	               if (currentTime - startTime >= timeLimit-0.5)
	                   return moves;
	        }
	    //south west
        if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
          {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
           if (!baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y-2)))
                moves.add (parentString);
           parentString +="\n";
           jumpMovesOutsideHome(colour, board, x+2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
          }
        //south east
        if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
            if (!baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y+2)))
            	moves.add (parentString);
            parentString +="\n";
           jumpMovesOutsideHome(colour, board, x+2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
        
     }
        
   else
        {
         // south east
        if (x +2 <=15 && y+2 <=15 && board[x+2][y+2] =='.' && board[x+1][y+1]!='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y+2)) )    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x+2);
            if (!baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y+2)))
            	moves.add (parentString);
            parentString +="\n";
            jumpMovesOutsideHome(colour, board, x+2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           	currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
      //north
        if (x -2 >=0 &&  board[x-2][y] =='.' && board[x-1][y]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y)))    
        {
          parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x-2);
          if ( !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y)))
                moves.add (parentString);
          parentString +="\n";
          jumpMovesOutsideHome(colour, board, x-2, y, moves,origX,origY, parentString,visited, baseIn, startTime,timeLimit);
          currentTime = System.currentTimeMillis();
          if (currentTime - startTime >= timeLimit-0.5)
            return moves; 
        }     
        //south
        if (x +2 <=15  && board[x+2][y] =='.' && board[x+1][y] !='.' && !visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y)+","+ String.valueOf(x+2);
           if ( !baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y)))
        	   moves.add (parentString);
           parentString +="\n";
           jumpMovesOutsideHome(colour, board, x+2, y, moves,origX,origY, parentString,visited, baseIn, startTime,timeLimit);    
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
        //east
        if (y+2 <=15 && board[x][y+2] =='.' && board[x][y+1]!='.' &&!visited.containsKey(String.valueOf(x) +","+ String.valueOf(y+2)) )    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x);
              if (!baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y+2)))
            	  moves.add (parentString);
           parentString +="\n";             
           jumpMovesOutsideHome(colour, board, x, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
        //south west
        if (x +2 <=15 && y-2 >=0 && board[x+2][y-2] =='.' && board[x+1][y-1]!='.' &&!visited.containsKey(String.valueOf(x+2) +","+ String.valueOf(y-2)) )    
          {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x+2);
           if (!baseIn.containsKey(String.valueOf(x+2)+","+String.valueOf(y-2)))
                moves.add (parentString);
            parentString +="\n";
            jumpMovesOutsideHome(colour, board, x+2, y-2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
            currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
          } 
         
        //north east
       if (x -2 >=0 && y+2 <=15 && board[x-2][y+2] =='.' && board[x-1][y+1]!='.' &&!visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y+2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y+2)+","+ String.valueOf(x-2);
          if (!baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y+2)))
        	  moves.add (parentString);
            parentString +="\n";
           jumpMovesOutsideHome(colour, board, x-2, y+2, moves,origX,origY, parentString,visited, baseIn, startTime, timeLimit);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
       
        //north west 
        if (x -2 >=0 && y-2 >=0 && board[x-2][y-2] =='.' && board[x-1][y-1]!='.' && !visited.containsKey(String.valueOf(x-2) +","+ String.valueOf(y-2)))    
        {
            parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x-2);
            if ( !baseIn.containsKey(String.valueOf(x-2)+","+String.valueOf(y-2)))
                moves.add (parentString);
            parentString +="\n";
            jumpMovesOutsideHome(colour, board, x-2, y-2, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
            currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }
      //west
        if (y-2 >=0 && board[x][y-2] =='.' && board[x][y-1]!='.' &&!visited.containsKey(String.valueOf(x) + ","+String.valueOf(y-2)))    
        {
           parentString += "J "+ String.valueOf(y)+","+ String.valueOf(x)+" "+String.valueOf(y-2)+","+ String.valueOf(x);
           if (!baseIn.containsKey(String.valueOf(x)+","+String.valueOf(y-2)))
              moves.add (parentString);
           parentString +="\n";
           jumpMovesOutsideHome(colour, board, x, y-2, moves,origX,origY, parentString,visited, baseIn,startTime, timeLimit);
           currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
        }   
     }
         
        
      return moves;  
   }
 
    public static ArrayList<String> getAllPossibleMoves(String colour, float timeLimit, char[][] board, long startTime)
    {
        ArrayList<String> moves = new ArrayList<String>();      
         
        HashMap<String , Integer > homebase = baseHmap(colour);
        String opponentColour = "";
        if (colour.equals ("WHITE"))
            opponentColour = "BLACK";
        else
            opponentColour = "WHITE";
        
        HashMap<String, Integer> opponentCoordinates = baseHmap(opponentColour);
        
        ArrayList<String> allPawnCoordinates = allThebase(board, colour);
        
        if (colour.equals("WHITE"))
        {
            for (int i= 0;i< allPawnCoordinates.size();i++)
            {         
               long currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
                
               String[] splitBaseLine = allPawnCoordinates.get(i).split(",");
               int x = Integer.parseInt(splitBaseLine[0]);
               int y = Integer.parseInt(splitBaseLine[1]);
               HashMap<String, Integer> visited = new HashMap<String, Integer>();
                  
               if (!opponentCoordinates.containsKey(String.valueOf(x)+","+String.valueOf(y)))         
               jumpMovesOutsideHome(colour, board, x, y, moves, x, y, "", visited, homebase, startTime, timeLimit);
 
               currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
               
               if (opponentCoordinates.containsKey(String.valueOf(x)+","+String.valueOf(y)))
                   opponentBaseMoves(colour, board, x, y, moves, x, y, "", visited, startTime, timeLimit, opponentCoordinates);
                  
                   currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
                            
               if (!opponentCoordinates.containsKey(String.valueOf(x)+ ","+String.valueOf(y)))
               {
            	   // north
                   if (x -1 >=0 && board[x-1][y]=='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x-1));            
                   }
                   // east      
                   if (y +1 <16 && board[x][y+1] =='.' && !homebase.containsKey(String.valueOf(x)+ ","+ String.valueOf(y+1)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x));     
                   }
                   // west
                   if (y -1 >=0 && board[x][y-1] =='.' && !homebase.containsKey(String.valueOf(x)+ ","+ String.valueOf(y-1)))
                   {
                    moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x)); 
                   }
                   //south
                   if (x +1 < 16  && board[x+1][y] =='.' &&!homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x+1));     
                   }
                   //north west
	               if (x -1 >=0 && y-1 >=0 && board[x-1][y-1]=='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y-1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x-1));                    
	               } 
	               // south-west move
	                if (x +1 <16  && y-1 >=0 && board[x+1][y-1] =='.' && !homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y-1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x+1));     
	               }
	                //south east
	               if (x +1 <16  && y+1 <16 && board[x+1][y+1] =='.' && !homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y+1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x+1));     
	               }
	               // adding north-east move
	              if (x -1 >=0  && y+1 <=15 && board[x-1][y+1] =='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y+1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x-1));     
	               }   
	              
           }

               else
               {   
            	    // north 
                   if (x -1 >=0  && board[x-1][y] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1) +"," + String.valueOf(y)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x-1));            
                   }
                   //east     
                   if (y +1 <16 && board[x][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x) +"," + String.valueOf(y+1)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x));     
                   }
                   // west 
                   if (y -1 >=0 && board[x][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x) +"," + String.valueOf(y-1)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x));                 
                   }
                   //south
                   if (x +1 < 16 && board[x+1][y] =='.' && opponentCoordinates.containsKey(String.valueOf(x+1) +"," + String.valueOf(y)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x+1));     
                   }
                   //north east
                   if (x -1 >=0  && y+1 <=15 && board[x-1][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1) +"," + String.valueOf(y+1)))
                   {
                     moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x-1));     
                   }   
                   // north-west move
	               if (x -1 >=0 && y-1 >=0 && board[x-1][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1) +"," + String.valueOf(y-1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x-1));                    
	               } 
	               //south west
	                if (x +1 <16  && y-1 >=0 && board[x+1][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x+1) +"," + String.valueOf(y-1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x+1));     
	               }
	                //south east
	               if (x +1 <16  && y+1 <16 && board[x+1][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x+1) +"," + String.valueOf(y+1)))
	               {
	                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x+1));     
	               }
               }                
            }         
        }
        
        else
        {
          for (int i= 0;i< allPawnCoordinates.size();i++)
            {
               String[] splitBaseLine = allPawnCoordinates.get(i).split(",");
               int x = Integer.parseInt(splitBaseLine[0]);
               int y = Integer.parseInt(splitBaseLine[1]);
               HashMap<String, Integer> visited =  new HashMap<String, Integer>();
               
                if (!opponentCoordinates.containsKey(String.valueOf(x)+","+String.valueOf(y)))  
                jumpMovesOutsideHome(colour, board, x, y, moves, x, y, "", visited, homebase, startTime,timeLimit);
                 long currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
               
               if (opponentCoordinates.containsKey(String.valueOf(x)+","+String.valueOf(y)))
                   opponentBaseMoves(colour, board, x, y, moves, x, y, "", visited, startTime, timeLimit, opponentCoordinates);
                   
               
                   currentTime = System.currentTimeMillis();
               if (currentTime - startTime >= timeLimit-0.5)
                   return moves;
                
               if (!opponentCoordinates.containsKey(String.valueOf(x)+","+ String.valueOf(y)))
               {               
              // south east
               if (x +1 <16  && y+1 <16  && board[x+1][y+1] =='.' && !homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y+1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x+1));     
               }
               //south
               if (x +1 < 16 && board[x+1][y] =='.'  && !homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x+1));     
               }
               //east  
               if (y +1 <16  && board[x][y+1] =='.' &&!homebase.containsKey(String.valueOf(x)+ ","+ String.valueOf(y+1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x));     
               }
               //nort east
               if (x -1 >=0  && y+1 <=15 && board[x-1][y+1] =='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y+1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x-1));     
               } 
               //west
                if (y -1 >=0 && board[x][y-1] =='.'  && !homebase.containsKey(String.valueOf(x)+ ","+ String.valueOf(y-1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x));   
               }
                //north
	           if (x -1 >=0 && board[x-1][y] =='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y)))
	           {
	             moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x-1));            
	           }
               //north west
               if (x -1 >=0 && y-1 >=0 && board[x-1][y-1] =='.' && !homebase.containsKey(String.valueOf(x-1)+ ","+ String.valueOf(y-1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x-1));                    
               } 
               //south west
                if (x +1 <16  && y-1 >=0 && board[x+1][y-1] =='.' && !homebase.containsKey(String.valueOf(x+1)+ ","+ String.valueOf(y-1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x+1));     
               }
                   
               }
           else
               {
                //north
               if (x -1 >=0  && board[x-1][y] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1)+","+ String.valueOf(y)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x-1));            
               }
               	//east    
               if (y +1 <16 && board[x][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x)+","+ String.valueOf(y+1)))
               {
                 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x));     
               }
               //west
				if (y -1 >=0 && board[x][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x)+","+ String.valueOf(y-1)))
				{
				  moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x)); 
				  
				}
				//south                
		         if (x +1 < 16 && board[x+1][y] =='.'  && opponentCoordinates.containsKey(String.valueOf(x+1)+","+ String.valueOf(y)))
		         {
		           moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y)+","+ String.valueOf(x+1));     
		         }
		         //south east
		         if (x +1 <16  && y+1 <16  && board[x+1][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x+1)+","+ String.valueOf(y+1)))
		         {
		        	 moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x+1));     
		         }
		         //north east
	             if (x -1 >=0  && y+1 <=15 && board[x-1][y+1] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1)+","+ String.valueOf(y+1)))
	              {
	                moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y+1)+","+ String.valueOf(x-1));     
	              }                               
                 //north west
	              if (x -1 >=0 && y-1 >=0 && board[x-1][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x-1)+","+ String.valueOf(y-1)))
	             {
	                moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x-1));                    
	             } 
	              //south west
	               if (x +1 <16  && y-1 >=0 && board[x+1][y-1] =='.' && opponentCoordinates.containsKey(String.valueOf(x+1)+","+ String.valueOf(y-1)))
	              {
	                moves.add ("E "+ String.valueOf(y)+","+String.valueOf(x)+" "+ String.valueOf(y-1)+","+ String.valueOf(x+1));     
	              }
               }
            }               
        }   
        return moves;
    }
    
    public static ArrayList<String> removeBaseFirst( char[][] board, String colour, float timeLimit, long startTime)
    {
    	
        ArrayList<String> moves = new ArrayList<String>();              
        HashMap<String, Integer> baseIn = baseHmap(colour); 
        ArrayList<String> homebase = new ArrayList<String>(baseIn.keySet());     
        long currentTime;
        if (colour.equals("WHITE"))
        {
            for (int i =0;i<homebase.size();i++)
                {   
                    currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >=timeLimit -0.5)
                    {
                      
                        return moves;
                    }
                        String pos = homebase.get(i);
                    String[] splitBaseLine =  pos.split(",");
                    int x = Integer.parseInt(splitBaseLine[0]);
                    int y = Integer.parseInt(splitBaseLine[1]);                 
                    if (board[x][y] =='W')
                    {
                    HashMap<String, Integer> visited = new HashMap<String, Integer>();
                 
                    jumpPriorities( board,colour,x, y, moves, x, y, "", visited, baseIn, startTime, (long)timeLimit);  
                    currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >=timeLimit -0.5)
                        return moves;
                  
                    if (board[x][y-1] =='.')        
                       moves.add("E "+y+","+x+" "+(y-1)+","+x);                                                                   
                      
                    if (board[x-1][y-1] =='.')
                        moves.add("E "+y+","+x+" "+(y-1)+","+(x-1));                                               
                   
                    if (board[x-1][y] =='.')
                        moves.add("E "+y+","+(x)+" "+y+","+(x-1));                                                          
                   
                    }              
                } 
            }
           
        else
        {            
           for (int i =0;i< homebase.size();i++)
           {
                    currentTime = System.currentTimeMillis();
                    if (currentTime - startTime >=timeLimit -1)
                        return moves;
                
                    String pos = homebase.get(i);
                    String[] splitBaseLine =  pos.split(",");
                    int x = Integer.parseInt(splitBaseLine[0]);
                    int y = Integer.parseInt(splitBaseLine[1]);
                    if(board[x][y]=='B')
                    {
                       HashMap<String,Integer> visited = new HashMap<String, Integer>();
                       jumpPriorities( board,colour, x, y, moves, x, y, "", visited, baseIn, startTime, (long)timeLimit);
                       currentTime = System.currentTimeMillis();
                       if (currentTime - startTime >=timeLimit -1)
                    	   return moves;
                      
                       if (board[x][y+1] =='.')
                        { 
                            moves.add("E "+y+","+x+" "+(y+1)+","+x);                                                          
                        }    
	                   if (board[x+1][y] =='.')
	                    {       
	                        moves.add("E "+y+","+x+" "+(y)+","+(x+1));                                                          
	                    }
	                   if (board[x+1][y+1] =='.')
	                    {
	                        moves.add("E "+y+","+x+" "+(y+1)+","+(x+1));                                                          
	                    }                                       
                }                      
           }              
        } 
        return moves;
    }
    public static ArrayList<String> moveSorting(ArrayList<String> allPossibleMoves, char[][] board, String colour)throws Exception
    {
        ArrayList<String> sortedMoves;
        HashMap<String, Float> utilityHash = new HashMap<String , Float>();
        for (int i =0;i<allPossibleMoves.size();i++)
        {
            String move = allPossibleMoves.get(i);
            makeMove(board, move);        
            float utilityForThisMove = utility(colour, board);
            utilityHash.put(move, utilityForThisMove);
            retractMove(move, board);    
        }        
        
       Map<String, Float> sortDescending = utilityHash.entrySet().stream().sorted(Map.Entry.<String, Float>comparingByValue().reversed())
               .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
               
        sortedMoves = new ArrayList<String>(sortDescending.keySet());   
        return sortedMoves;
    }
       
    public static void makeMove (char[][] board, String move)
    {  
        String[] lineSplit = move.split("\n");
        String finalCoordLine = lineSplit[lineSplit.length-1];
        String[] spaceSplit =finalCoordLine.split("\\s+");
        String finalbase = spaceSplit[2];
        String[] commaSplit = finalbase.split(",");
        int endx = Integer.parseInt(commaSplit[1]);
        int endy = Integer.parseInt(commaSplit[0]);
        
        String initialCoordLine = lineSplit[0];
        spaceSplit = initialCoordLine.split("\\s+");
        String initialbase = spaceSplit[1];
        commaSplit = initialbase.split(",");
        int startx = Integer.parseInt(commaSplit[1]);
        int starty = Integer.parseInt(commaSplit[0]);
 
       char playerPawn = board[startx][starty];
       board[startx][starty] = '.';
       board[endx][endy] = playerPawn;
   
       return;      
    }
            
    public static void retractMove (String move, char[][] board)
    {
        String[] lineSplit = move.split("\n");
        String finalCoordLine = lineSplit[lineSplit.length-1];
        String[] spaceSplit =finalCoordLine.split("\\s+");
        String finalbase = spaceSplit[2];
        String[] commaSplit = finalbase.split(",");
        int endx = Integer.parseInt(commaSplit[1]);
        int endy = Integer.parseInt(commaSplit[0]);
        
        String initialCoordLine = lineSplit[0];
        spaceSplit = initialCoordLine.split("\\s+");
        String initialbase = spaceSplit[1];
        commaSplit = initialbase.split(",");
        int startx = Integer.parseInt(commaSplit[1]);
        int starty = Integer.parseInt(commaSplit[0]);
        
        char playerPawn = board[endx][endy];
        board[endx][endy] = '.';
        board[startx][starty] = playerPawn;   
        return;
    }
    
}