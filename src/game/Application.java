package game;

import java.util.Arrays;
import java.util.List;


import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			Bonfire fireLinkShrine = new Bonfire();

			Bonfire anorLondoShrine = new Bonfire();

			FogDoor profaneCapitalFogDoor = new ProfaneCapitalFogDoor();

			FogDoor anorLondoFogDoor = new AnorLondoFogDoor();

			FancyGroundFactory groundFactory1 = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Valley(), new Cemetery(), fireLinkShrine, profaneCapitalFogDoor, new Chest());

			FancyGroundFactory groundFactory2 = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Valley(), new Cemetery(), anorLondoShrine, anorLondoFogDoor);

			List<String> profaneCapital = Arrays.asList(
					"..++++++..+++...........................++++......+++.................+++.......",
					"........+++++..............................+++++++.................+++++........",
					"...........+++.....................................................c.+++++......",
					"........................................................................++......",
					"................................................................?........+++....",
					"............................+.............................................+++...",
					".............................+++.......++++.....................................",
					".............................++.......+......................++++...............",
					".............................................................+++++++............",
					"..................................###___###...................+++...............",
					"..................................#_______#......................+++..........?.",
					"...........++...........?.........#___B___#.......................+.............",
					".........+++......................#_______#........................++...........",
					"............+++...................####_####..........................+..........",
					"...c..........+......................................................++.........",
					"..............++..............?..................................++++++.........",
					"............+++.....................?.?...........................++++..........",
					"+......c...........................................................++...........",
					"++...+++............................?............................++++........c..",
					"+++......................................+++........................+.++........",
					"++++.......++++.........................++..............?..........+....++......",
					"#####___#####++++......................+...............................+..+.....",
					"_..._....._._#.++......................+...................................+....",
					"...+.__..+...#+++................................................c..........+...",
					"...+.....+._.#.+.....+++++...++..............................................++.",
					"=============#==================================================================");


			List<String> anorLondo = Arrays.asList(
					"=============#==================================================================",
					".............#..................................................................",
					".....#.......#...................++++...........................................",
					"...............................+++...........................c...+.++...........",
					"...............................++...............................++..............",
					".......#........................++..........................+++++...............",
					"...............................+++.........................++...................",
					"......###___###...............+++++.............................................",
					"......#_______#.................................................................",
					"......#___B___#.................................................................",
					"......#_______#......................................................++.........",
					"......####_####..........................................++.........++..........",
					"..........................................................++++......++..........",
					"............................................................+++++....+++........",
					"......................c...++++++++++...........................++++..++.........",
					"..........................++++....................................++++..........",
					".................+++......+++...................................................",
					"...................+++...+++....................................................",
					"....................++++++++..........................+++.........++............",
					"........+++..........................................++.......++.++#####___#####",
					"......++...................................................+++++#____._......___",
					"......++...................................................+++++#.._.__.____.__.",
					"......++...................................................+++++#_.._.___._.__..",
					".....+++...c......................................c..........+++#..__._+_...___.",
					".....++.......................................................++#......+_..._.__",
					"......++......................................................++#_._...__..___..");

			// Initialise the game map for the Profane Capital.
			GameMap gameMap1 = new GameMap(groundFactory1, profaneCapital);
			world.addGameMap(gameMap1);
			MapManager.appendMap("Profane Capital", gameMap1);

			// Initialise the game map for the Anor Londo.
			GameMap gameMap2 = new GameMap(groundFactory2, anorLondo);
			world.addGameMap(gameMap2);
			MapManager.appendMap("Anor Londo", gameMap2);

			// Append a restable instance (i.e. Firelink Shrine) to the Bonfire Manger.
			BonfireManager.appendRestableInstance("Firelink Shrine", gameMap1.at(38,11));
			BonfireManager.setLastInteractedBonfire("Firelink Shrine");

			// Allow Firelink Shrine to be named and lit.
			fireLinkShrine = (Bonfire) gameMap1.at(38,11).getGround();
			fireLinkShrine.setBonfireName("Firelink Shrine");
			fireLinkShrine.lit();

			// Append a restable instance (i.e. Anor Londo's Shrine) to the Bonfire Manger.
			BonfireManager.appendRestableInstance("Anor Londo's Shrine", gameMap2.at(10,9));

			// Allow Firelink Shrine to be named.
			anorLondoShrine = (Bonfire) gameMap2.at(10,9).getGround();
			anorLondoShrine.setBonfireName("Anor Londo's Shrine");

			// Initialise and add a player (i.e. The Unkindled) to the Profane Capital.
			Actor player = new Player("Unkindled", '@', 100, gameMap1);
			world.addPlayer(player, gameMap1.at(38, 12));

			// Initialise and equip a broadsword to the player (i.e. The Unkindled).
			Broadsword playerWeapon = new Broadsword("Broadsword",'b',30,"slashes",80);
			player.addItemToInventory(playerWeapon);

			// Initialise and add a vendor (i.e. The Fire Keeper) to the Profane Capital.
			Actor vendor = new Vendor("Fire Keeper", 'F', 100);
			gameMap1.at(37, 11).addActor(vendor);

			// Initialise and add a hostile enemy (i.e. Yhorm the Giant) to the Profane Capital.
			Actor bossCinder1 = new YhormTheGiant("Yhorm the Giant", 'Y', 500);
			gameMap1.at(6, 25).addActor(bossCinder1);



			// Initialise and equip a machete to the Yhorm the Giant.
			Machete boss_machete = new Machete("Yhorm's Great Machete",'M',95, "slashes",60);
			bossCinder1.addItemToInventory(boss_machete);

			// Initialise and place a Storm Ruler weapon in the Profane Capital.
			StormRuler stormRuler = new StormRuler("Storm Ruler",'7',70,"strikes",60);
			gameMap1.at(7,25).addItem(stormRuler);

			// Initialise a broadsword.
			Broadsword broadsword = new Broadsword("Broadsword",'b',30,"slashes",80);

			// Initialise a giant axe.
			GiantAxe giantAxe = new GiantAxe("GiantAxe", 'g', 50, "slashes", 80);

			// Initialise and place skeletons on the map of Profane Capital. Then, initialise and equip them with random
			// weapons (i.e. Broadsword or giant axe).
			Actor skeleton1 = new Skeleton("Skeleton");
			gameMap1.at(38, 4).addActor(skeleton1);
			skeleton1.addItemToInventory(giantAxe);

			Actor skeleton2 = new Skeleton("Skeleton");
			gameMap1.at(25, 10).addActor(skeleton2);
			skeleton2.addItemToInventory(broadsword);

			Actor skeleton3 = new Skeleton("Skeleton");
			gameMap1.at(46, 25).addActor(skeleton3);
			skeleton3.addItemToInventory(giantAxe);

			Actor skeleton4 = new Skeleton("Skeleton");
			gameMap1.at(8, 17).addActor(skeleton4);
			skeleton4.addItemToInventory(broadsword);

			Actor skeleton5 = new Skeleton("Skeleton");
			gameMap1.at(31, 12).addActor(skeleton5);
			skeleton5.addItemToInventory(giantAxe);

			Actor skeleton6 = new Skeleton("Skeleton");
			gameMap1.at(10, 20).addActor(skeleton6);
			skeleton6.addItemToInventory(giantAxe);

//			Second profaneCapital stuff
			Actor bossCinder2 = new AldrichTheDevourer("Aldrich the Devourer",'A',350);


			DarkmoonLongbow longbow = new DarkmoonLongbow("Darkmoon Longbow",'l',70,"shoots",80);

			bossCinder2.addItemToInventory(longbow);
			gameMap2.at(72, 23).addActor(bossCinder2);
//			gameMap2.at(38, 17).addActor(bossCinder2);


			Actor skeleton7 = new Skeleton("Skeleton");
			gameMap2.at(38, 14).addActor(skeleton7);
			skeleton1.addItemToInventory(giantAxe);


			Actor skeleton8 = new Skeleton("Skeleton");
			gameMap2.at(31, 12).addActor(skeleton8);
			skeleton2.addItemToInventory(broadsword);


			Actor skeleton9 = new Skeleton("Skeleton");
			gameMap2.at(46, 25).addActor(skeleton9);
			skeleton3.addItemToInventory(giantAxe);


			Actor skeleton10 = new Skeleton("Skeleton");
			gameMap2.at(8, 17).addActor(skeleton10);
			skeleton4.addItemToInventory(broadsword);


			Actor skeleton11 = new Skeleton("Skeleton");
			gameMap2.at(25, 10).addActor(skeleton11);
			skeleton5.addItemToInventory(giantAxe);



			world.run();

	}
}
