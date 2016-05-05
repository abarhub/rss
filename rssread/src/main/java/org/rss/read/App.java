package org.rss.read;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final Logger logger = LoggerFactory.getLogger(App.class);

    public /*static*/ void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //test1();
        test2();
    }

    private static void test1() {
        ReadHttp read;
        String res="";
        read=new ReadHttp();
        try {
            res=read.read("http://static.userland.com/gems/backend/rssTwoExample2.xml");
            System.out.println("res="+res+"!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        ReadHttp read;
        ParseRss parse;
        String res="";
        read=new ReadHttp();
        try {
            res=read.read("http://static.userland.com/gems/backend/rssTwoExample2.xml");
            System.out.println("res="+res+"!");
            parse=new ParseRss();
            ResultatRss res2= parse.read(res);
            Preconditions.checkNotNull(res2);
            if(res2.isError())
            {
                logger.info("Error:"+res2.getErrors());
            }
            else
            {
                Preconditions.checkNotNull(res2.getRes());
                logger.info("Parse OK");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
