package tp.p2.controller.Commands;

import tp.p2.game.Game;

/**
 * @author Fernando M�ndez Torrubiano
 *
 */

public class ListCommand extends Command {

	public ListCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(
				"[R]egular ship: Points: 5 - Harm: 0 - Shield: 2 \n" +
				"[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1 \n" +
				"[E]xplosive destroyer ship: Points: 10 - Harm: 1 - Shield: 1 \n" +		
				"[O]vni: Points: 25 - Harm: 0 - Shield: 1 \n" +
				"^__^: Harm: 1 - Shield: 3\n");
		return false;
	}

	@Override
	public Command parse(String[] commandWords) {
		Command cmd = null;
		
		if(commandWords[0].equals("list") || commandWords[0].equals("l")) {
			cmd = this;
		}
		
		return cmd;
	}

}
