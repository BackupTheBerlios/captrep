indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/17 14:46:05 $"
	revision: "$Revision: 1.1 $"

class
	POI_RESOLUTION

create
	make
	
feature {CAPTURE_REPLAY}
	
	make (p: like poi ; o : like object) is
			-- initialize
		do
			poi := p
			object := o
		end
		
feature {CAPTURE_REPLAY}

	poi: CAPTURE_REPLAY_POI
	
	object: WEL_WINDOW

end -- class POI_RESOLUTION
