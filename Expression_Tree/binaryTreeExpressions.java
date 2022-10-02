package WebCrawler;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class binaryTreeExpressions
{

    /*
    (1) printing the expression before it is manipulated;
   (2) showing the converted postfix form expression; keep popping from stack into queue 1 + 2 * 3 ^ ! will be 1 2 3 ^ * +
   (3) showing the expression tree;
   (4) printing the fully parenthesized infix form expression;
   (5) reporting the value of the expression.
   (6) program needs to stop and ask user to press <Enter> to continue sequence
     */
    public static void main(String[] args) throws IOException
    {

        Scanner in = new Scanner(new File("C:\\Users\\Jeremy\\OneDrive\\Desktop\\Algorithms\\WebCrawler\\testdata.txt"));


        while(in.hasNextLine())
        {

            Stack<String> stack = new Stack<>();
            Queue<String> output = new LinkedList<>();
            Scanner input = new Scanner(System.in); //use this to promt user the<Enter>

            int index = 0;

            String line = in.nextLine();
            String[] tokens = line.split(" ");
            System.out.print("Expression Before Manipulation: ");
            for(int i = 0; i < tokens.length; i++)
            {
                System.out.print(tokens[i] + " ");
            }
            System.out.println();

            int currentTokenValue = 0;
            int topStackValue = 0;

            do {
                currentTokenValue = precedence(tokens[index]);

                if(stack.isEmpty())
                {
                    topStackValue = 0;
                }
                else
                {
                    topStackValue = precedence(stack.peek());
                }

                switch (tokens[index]) //this is a string
                {

                    case "!","^","*", "/", "%","+","-","<", "<=", ">", ">=","==", "!=","&&","||":
                        //associativity(stack, output, tokens, index, topStackValue, currentTokenValue);
                        while((!stack.isEmpty() && !leftParenthesis(stack.peek()) && topStackValue >= currentTokenValue) && !(stack.peek().equals("^")))
                        {

                            output.add(stack.pop());

                            if(stack.isEmpty())
                            {
                                topStackValue = 0;
                            }
                            else
                            {
                                topStackValue = precedence(stack.peek());
                            }
                        }
                        stack.push(tokens[index]);
                        //output.add(tokens[index]);
                        break;

                    case "(":
                        stack.push(tokens[index]);
                        break;

                    case ")":
                        while(!leftParenthesis(stack.peek()))//while it is not a left ( keep popping until it is
                        {
                            output.add(stack.pop());
                        }
                        stack.pop(); //pop off the left parenthesis
                        break;

                    case "$":
                        while(!stack.isEmpty())
                        {
                            //pop everything and place onto the output
                            output.add(stack.pop());

                        }
                        break;
                    default://if its a number place it on the output

                        if(!stack.isEmpty())
                        {
                            topStackValue = precedence(stack.peek());
                        }

                        output.add(tokens[index]); //add to the output queue
                        break;

                }


                index++;
            }while(index != tokens.length);

            //do the evaluation of postfix here
            Queue<String> copy = new LinkedList<>();
            Queue<String> copy2 = new LinkedList<>();
            Queue<String> printPostFix = new LinkedList<>(); //print postorder traversal instead

            while(!output.isEmpty())
            {
                String token = output.remove();
                copy.add(token);
                copy2.add(token);
                printPostFix.add(token);
            }

            int answer = evaluation(copy, stack);
            System.out.print("The Postfix Expression is: ");

            while(!printPostFix.isEmpty()) //print post order traversal instead
            {
                System.out.print(printPostFix.remove() + " ");
            }

            System.out.println();

            Stack<BTNode<String>> expressionTree = new Stack<BTNode<String>>();
            System.out.print("The Fully Parenthisized Infix Expression is: ");


            while(!copy2.isEmpty())//this is to create the expression tree using the postfix i created above
            {
                String postFixToken = copy2.remove();


                if(Character.isDigit(postFixToken.charAt(0)))
                {
                    BTNode<String> children = new BTNode<String>(postFixToken, null, null);
                    expressionTree.push(children);
                }
                else if(!expressionTree.isEmpty() && !(postFixToken.equals("!")))
                {//problem is to check the unary operators that can only have 1 child !
                    BTNode<String> rightChild = expressionTree.pop();
                    BTNode<String> leftChild = expressionTree.pop();
                    BTNode<String> parent = new BTNode<String>(postFixToken, leftChild, rightChild);
                    expressionTree.push(parent);
                }
                else if(expressionTree.isEmpty() && !(Character.isDigit(postFixToken.charAt(0))))
                {
                    BTNode<String> children = new BTNode<String>(postFixToken, null, null);
                    expressionTree.push(children);
                }
                else
                {
                    BTNode<String> rightChildUnary = expressionTree.pop();
                    BTNode<String> parentUnary = new BTNode<String>(postFixToken, null, rightChildUnary);
                    expressionTree.push(parentUnary);
                }


            }
            BTNode<String> root = expressionTree.pop();
            root.inorderPrint();
            System.out.println();
            System.out.println("The evaluation of the Expression is: " +answer);
            root.print2DUtil(root, 0, 10);

            System.out.println();
            System.out.println("Would You Like To Continue? Enter: yes/no");
            String halt = input.nextLine();

            int attempts = 5;
            while(attempts > 0)
            {

                if(halt.equals("no"))
                {
                    System.out.println("Program Terminated");
                    System.exit(1);
                }
                else if(halt.equals("yes"))
                {
                    System.out.println("----------------------------------------------------------------\n");
                    break;
                }
                else
                {
                    attempts--;
                    System.out.println("Invalid Input, You Have: " + attempts + " Left");

                }
                halt = input.next();
            }

        }


        in.close();

    }

    public static int evaluation(Queue<String> postfixExpression, Stack<String> stack)
    {

        int result = 0;
        int tokenNumber1;
        int tokenNumber2;

        while(!postfixExpression.isEmpty())
        {
            String token = postfixExpression.remove();
            switch (token)
            {
                case "!":
                    tokenNumber1 = Integer.parseInt(stack.pop());

                    boolean logicalNot = intToBool(~(tokenNumber1));//logical not ~ or ! flips bits Example: 10111(23) now not it and it becomes 01000(8)
                    result = boolToInt(logicalNot);
                    if(tokenNumber1 == 0)
                    {
                        result = 1;
                    }
                    stack.push(String.valueOf(result));
                    break;

                case "^":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    stack.push(String.valueOf((int) (Math.pow(tokenNumber2, tokenNumber1)))); //check parenthesis "here"
                    break;

                case "*":
                    stack.push(String.valueOf(Integer.parseInt(stack.pop()) * Integer.parseInt(stack.pop())));
                    break;

                case "/":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    try{
                        if (tokenNumber2 != 0) //cannot divide by zero!
                        {
                            stack.push(String.valueOf(tokenNumber2 / tokenNumber1)); //check order
                        }
                    }catch(ArithmeticException e)
                    {
                        System.out.println("Division by zero");
                    }
                    break;

                case "%":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    stack.push(String.valueOf(tokenNumber2 % tokenNumber1)); //reverse order to subtract "check later"
                    break;
                case "+":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    stack.push(String.valueOf(tokenNumber2 + tokenNumber1));
                    break;
                case "-":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    stack.push(String.valueOf(tokenNumber2 - tokenNumber1)); //reverse order to subtract "check later"
                    break;

                case "<":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber2 < tokenNumber1);
                    stack.push(String.valueOf(result));
                    break;

                case "<=":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber1 <= tokenNumber2);
                    stack.push(String.valueOf(result));
                    break;

                case ">":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber2 > tokenNumber1);
                    stack.push(String.valueOf(result));
                    break;

                case ">=":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber1 >= tokenNumber2);
                    stack.push(String.valueOf(result));
                    break;

                case "==":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber1 == tokenNumber2);
                    stack.push(String.valueOf(result));//convert back to string and push onto stack
                    break;

                case "!=":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    result = boolToInt(tokenNumber1 != tokenNumber2);
                    stack.push(String.valueOf(result));
                    break;

                case "&&":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    boolean boolAnswer = intToBool(tokenNumber1);
                    boolean boolAnswer2 = intToBool(tokenNumber2);
                    result = boolToInt(boolAnswer && boolAnswer2);
                    stack.push(String.valueOf(result));
                    break;

                case "||":
                    tokenNumber1 = Integer.parseInt(stack.pop());
                    tokenNumber2 = Integer.parseInt(stack.pop());
                    boolAnswer = intToBool(tokenNumber1);
                    boolAnswer2 = intToBool(tokenNumber2);
                    result = boolToInt(boolAnswer || boolAnswer2);
                    stack.push(String.valueOf(result));
                    break;

                default:
                    //if its a number we push it onto the stack
                    stack.push(token);

            }

        }
        return Integer.parseInt(stack.pop());
    }


    public static boolean intToBool(int value)
    {
        return value > 0;
    }

    public static int boolToInt(boolean value)
    {
        return (value) ? 1 : 0;
    }

    public static boolean leftParenthesis(String s)
    {
        return s.equals("(");
    }

    public static int precedence(String stackTop)//pass in token operator
    {

        switch (stackTop)
        {
            case "!":
                return 8;

            case "^":
                return 7;

            case "*", "/", "%":
                return 6;

            case "+", "-":
                return 5;

            case "<", "<=", ">", ">=":
                return 4;

            case "==", "!=":
                return 3;

            case "&&":
                return 2;

            case "||":
                return 1;

            default:
                return 0;

        }
    }




}
