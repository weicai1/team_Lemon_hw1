package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.function.Consumer;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Arrays;

@Controller
public class GreetingController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String asd(Model model, HttpServletRequest request) {
        model.addAttribute("login", new User());
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") != null) {
            return "redirect:/hello";
        }
        if (session.getAttribute("state") != null) {
            if (session.getAttribute("state").equals(3)) {
                session.setAttribute("state", 4);
            } else if (session.getAttribute("state").equals(4)) {
                session.setAttribute("state", null);
            } else if (session.getAttribute("state").equals(2)) {
                session.setAttribute("state", null);
            }
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(Model model, @ModelAttribute User user, HttpServletRequest request) {
        model.addAttribute("login", user);
        Iterable<User> a = userRepository.findAll();
        HttpSession session = request.getSession();
        for (User u : a) {
            if (u.getName().equals(user.getName()) && u.getPassword().equals(user.getPassword())) {
                session.setAttribute("Name", user.getName());
                session.setAttribute("state", 1);
                return "redirect:/hello";
            }
        }
        session.setAttribute("state", 2);
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("Name", null);
        session.setAttribute("state", 3);
        return "redirect:/login";
    }

    @RequestMapping("/detail")
    public String detail(Model model,@ModelAttribute User ug,HttpServletRequest request) {
        model.addAttribute("ug", ug);
        HttpSession session=request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        String date=(String)request.getParameter("date");
        String name=(String)session.getAttribute("Name");
        ArrayList<ArrayList<String>> up = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> ap = new ArrayList<ArrayList<String>>();
        JSONParser jsonParser = new JSONParser();
        File file = new File(name+".json");

        if(!file.exists()){
            return null;
        }
        else {
            try (FileReader reader = new FileReader(name + ".json")) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject games = (JSONObject) obj;
                JSONArray userai=(JSONArray)games.get(date);
                up=(ArrayList<ArrayList<String>>)userai.get(0);
                ap=(ArrayList<ArrayList<String>>)userai.get(1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        session.setAttribute("userpast",up);
        session.setAttribute("aipast",ap);
        return "/detail";
    }


    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        return "hello";
    }

    @GetMapping("/signup")
    public String signup(Model model, HttpServletRequest request) {
        model.addAttribute("signup", new User());
        HttpSession session = request.getSession();
        session.setAttribute("signupstate", null);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Model model, @ModelAttribute User user, HttpServletRequest request) {
        model.addAttribute("signup", new User());
        Iterable<User> a = userRepository.findAll();
        HttpSession session = request.getSession();
        for (User u : a) {
            if (u.getName().equals(user.getName())) {
                session.setAttribute("signupstate", "2");
                return "signup";
            }
        }
        userRepository.save(user);
        session.setAttribute("Name", user.getName());
        return "redirect:/hello";
    }

    @RequestMapping("/")
    public String home(Model model, @ModelAttribute User user, HttpServletRequest request) {
        return "/home";
    }

    @RequestMapping("/home")
    public String home2(Model model, @ModelAttribute User user, HttpServletRequest request) {
        return "/home";
    }

    @GetMapping("/game")
    public String game(Model model, @ModelAttribute User ug, HttpServletRequest request) throws Exception {
        model.addAttribute("ug", ug);
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        jotto j = new jotto();
        session.setAttribute("jotto", j);
        session.setAttribute("aisecret", j.secret);
        session.setAttribute("step", 1);
        ArrayList<ArrayList<String>> userguess = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> aiguess = new ArrayList<ArrayList<String>>();
        ArrayList<String> redset = new ArrayList<String>();
        ArrayList<String> greenset = new ArrayList<String>();
        session.setAttribute("gamestate", null);
        session.setAttribute("userguess", userguess);
        session.setAttribute("aiguess", aiguess);
        session.setAttribute("wordList", j.wordList);
        session.setAttribute("redset", redset);
        session.setAttribute("greenset", greenset);
        return "game";
    }

    @GetMapping("/back")
    public String back(Model model, @ModelAttribute User ug, HttpServletRequest request) throws Exception {
        model.addAttribute("ug", ug);
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        jotto j = new jotto();
        session.setAttribute("jotto", j);
        session.setAttribute("aisecret", j.secret);
        session.setAttribute("step", 1);
        ArrayList<ArrayList<String>> userguess = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> aiguess = new ArrayList<ArrayList<String>>();
        ArrayList<String> redset = new ArrayList<String>();
        ArrayList<String> greenset = new ArrayList<String>();
        session.setAttribute("gamestate", null);
        session.setAttribute("userguess", userguess);
        session.setAttribute("aiguess", aiguess);
        session.setAttribute("wordList", j.wordList);
        session.setAttribute("redset", redset);
        session.setAttribute("greenset", greenset);
        return "redirect:/hello";
    }


    @GetMapping("/aiturn")
    public String gs(Model model, @ModelAttribute User ug, HttpServletRequest request) {
        model.addAttribute("ug", ug);
        String uu = (String) request.getParameter("value");
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        ArrayList<ArrayList<String>> aiguess = (ArrayList<ArrayList<String>>) session.getAttribute("aiguess");
        jotto j = (jotto) session.getAttribute("jotto");
        //String secret = (String) session.getAttribute("aisecret");
        String pick = (String) session.getAttribute("currentpick");
        //String user = (String) session.getAttribute("Name");
        //ArrayList<String> wl = (ArrayList<String>) session.getAttribute("wordList");
        int step = (int) session.getAttribute("step");
        ArrayList<String> temp = new ArrayList<String>();
        String uu2 = uu;
        uu2 = pick + ":" + uu;
        for (int i = 0; i < uu2.length(); i++) {
            temp.add("" + uu2.charAt(i));
        }
        aiguess.add(temp);

        if (uu.equals("Yes")) {
            String name = (String) session.getAttribute("Name");

            ArrayList<ArrayList<String>> ag = (ArrayList<ArrayList<String>>) session.getAttribute("aiguess");
            ArrayList<ArrayList<String>> userg = (ArrayList<ArrayList<String>>) session.getAttribute("userguess");
            String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            JSONArray userWords = new JSONArray();
            JSONArray aiWords = new JSONArray();
            for (int i = 0; i < userg.size(); i++) {
                ArrayList<String> w = userg.get(i);
                userWords.add(w);
            }
            for (int i = 0; i < ag.size(); i++) {
                ArrayList<String> w = ag.get(i);
                aiWords.add(w);
            }

            JSONObject oneGame = new JSONObject();
            JSONArray date = new JSONArray();
            oneGame.put(time, date);
            date.add(userWords);
            date.add(aiWords);
            JSONParser jsonParser = new JSONParser();
            File file = new File(name + ".json");
            if (!file.exists()) {
                try (FileWriter fi = new FileWriter(name+".json")) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (FileReader reader = new FileReader(name + ".json")) {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);

                    JSONObject games = (JSONObject) obj;
                    //System.out.println(games);
                    for (Object s : games.keySet()) {
                        oneGame.put((String) s, games.get((String) s));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter f = new FileWriter(name + ".json")) {

                f.write(oneGame.toJSONString());
                f.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("end");
            return "aiwin";
        }
        j.process2(step-1, Integer.parseInt(uu), pick);
        String test=j.process1(step);
        if(test.equals("You got wrong!")){

            String tp=" WRONG ";
            ArrayList<String> t3=new ArrayList<String>();
            for(int i=0;i<tp.length();i++){
                t3.add(tp.charAt(i)+"");
            }
            String name = (String) session.getAttribute("Name");
            ArrayList<ArrayList<String>> ag = (ArrayList<ArrayList<String>>) session.getAttribute("aiguess");
            ArrayList<ArrayList<String>> userg = (ArrayList<ArrayList<String>>) session.getAttribute("userguess");
            ag.add(t3);
            String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
            JSONArray userWords = new JSONArray();
            JSONArray aiWords = new JSONArray();
            for (int i = 0; i < userg.size(); i++) {
                ArrayList<String> w = userg.get(i);
                userWords.add(w);
            }
            for (int i = 0; i < ag.size(); i++) {
                ArrayList<String> w = ag.get(i);
                aiWords.add(w);
            }

            JSONObject oneGame = new JSONObject();
            JSONArray date = new JSONArray();
            oneGame.put(time, date);
            date.add(userWords);
            date.add(aiWords);
            JSONParser jsonParser = new JSONParser();
            File file = new File(name + ".json");
            if (!file.exists()) {
                try (FileWriter fi = new FileWriter(name+".json")) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try (FileReader reader = new FileReader(name + ".json")) {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);

                    JSONObject games = (JSONObject) obj;
                    //System.out.println(games);
                    for (Object s : games.keySet()) {
                        oneGame.put((String) s, games.get((String) s));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            try (FileWriter f = new FileWriter(name + ".json")) {

                f.write(oneGame.toJSONString());
                f.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "gotwrong";
       }
        session.setAttribute("gamestate", null);
        return "game";
    }


    @GetMapping("/changecolor")
    public String cc(Model model, @ModelAttribute User ug, HttpServletRequest request) {
        model.addAttribute("ug", ug);
        String from_ = (String) request.getParameter("from");
        String color = (String) request.getParameter("color");
        String letter = (String) request.getParameter("letter");
        HttpSession session = request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        ArrayList<String> redset = (ArrayList<String>) session.getAttribute("redset");
        ArrayList<String> greenset = (ArrayList<String>) session.getAttribute("greenset");
        if (color.equals("red")) {
            redset.add(letter);
        } else if (color.equals("green")) {
            greenset.add(letter);
            redset.remove(letter);
        } else if (color.equals("black")) {
            greenset.remove(letter);
        }
        if (from_.equals("game2")) {
            return "game2";
        }
        return "game";
    }

    @PostMapping("/game")
    public String postgame(Model model,@ModelAttribute User ug,HttpServletRequest request) {
        model.addAttribute("ug", ug);
        HttpSession session=request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        String uu = ug.getName();
        jotto j=(jotto)session.getAttribute("jotto");
        String secret=(String)session.getAttribute("aisecret");
        ArrayList<String> wl=(ArrayList<String>)session.getAttribute("wordList");
        System.out.println(secret);
        if(session.getAttribute("gamestate")==null){
            if (wl.indexOf(uu)<0) {
                return "gamebadguess";
            }
            //good guess
            else{
                ArrayList<ArrayList<String>> userguess=(ArrayList<ArrayList<String>>)session.getAttribute("userguess");
                ArrayList<String> temp=new ArrayList<String>();
                for(int i=0;i<uu.length();i++){
                    temp.add(""+uu.charAt(i));
                }
                temp.add(":");
                if(uu.equals(secret)){
                    temp.add("Y");
                }
                else {
                    temp.add(""+j.count(uu));
                }
                userguess.add(temp);
                //user wins
                if(uu.equals(secret)){
                    String name=(String)session.getAttribute("Name");
                    ArrayList<ArrayList<String>> ag=(ArrayList<ArrayList<String>>)session.getAttribute("aiguess");
                    ArrayList<ArrayList<String>> userg=(ArrayList<ArrayList<String>>)session.getAttribute("userguess");
                    String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
                    JSONArray userWords = new JSONArray();
                    JSONArray aiWords = new JSONArray();
                    for(int i=0;i<userg.size();i++){
                        ArrayList<String> w=userg.get(i);
                        userWords.add(w);
                    }
                    for(int i=0;i<ag.size();i++){
                        ArrayList<String> w=ag.get(i);
                        aiWords.add(w);
                    }

                    JSONObject oneGame = new JSONObject();
                    JSONArray date = new JSONArray();
                    oneGame.put(time,date);
                    date.add(userWords);
                    date.add(aiWords);
                    JSONParser jsonParser = new JSONParser();
                    File file = new File(name+".json");
                    if(!file.exists()){
                        try (FileWriter fi = new FileWriter(name+".json")) {
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try (FileReader reader = new FileReader(name + ".json")) {
                            //Read JSON file
                            Object obj = jsonParser.parse(reader);

                            JSONObject games = (JSONObject) obj;
                            //System.out.println(games);
                            for ( Object s : games.keySet() ) {
                                oneGame.put((String)s,games.get((String)s));
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    try (FileWriter f = new FileWriter(name+".json")) {
                        f.write(oneGame.toJSONString());
                        f.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "userwin";
                }
                int step = (int)session.getAttribute("step");
                String pick=j.process1(step);
                session.setAttribute("step",step+1);
                session.setAttribute("currentpick",pick);
                session.setAttribute("gamestate",2);
                return "game2";
            }
        }
        //    if(session.getAttribute("gamestate").equals(2)){
        //       ArrayList<String> aiguess=(ArrayList<String>)session.getAttribute("aiguess");
        //       uu = ug.getPassword();
        //       aiguess.add(uu);
        //       session.setAttribute("gamestate",null);
        //       return "/game";
        //  }
        return "game";
    }




    @RequestMapping("/pastgame")
    public String pastgame(Model model,@ModelAttribute User ug,HttpServletRequest request) {
        model.addAttribute("ug", ug);
        HttpSession session=request.getSession();
        if (session.getAttribute("Name") == null) {
            return "redirect:/login";
        }
        String name=(String)session.getAttribute("Name");
        ArrayList<String> games = new ArrayList<String>();
        JSONParser jsonParser = new JSONParser();
        File file = new File(name+".json");
        session.setAttribute("list",games);
        if(!file.exists()){
            try (FileReader reader = new FileReader(name + ".json")) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject g = (JSONObject) obj;
                //System.out.println(games);
                Object[] keys = g.keySet().toArray();
                Arrays.sort(keys);
                for ( Object key : keys ) {
                    games.add((String)key);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }
        else {
            try (FileReader reader = new FileReader(name + ".json")) {
                //Read JSON file
                Object obj = jsonParser.parse(reader);

                JSONObject g = (JSONObject) obj;
                //System.out.println(games);
                Object[] keys = g.keySet().toArray();
                Arrays.sort(keys);
                for ( Object key : keys ) {
                    games.add((String)key);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "/pastgame";
    }

    }