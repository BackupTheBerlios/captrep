indexing
	description: "Objects that ..."
	author: ""
	date: "$Date: 2004/05/17 14:45:55 $"
	revision: "$Revision: 1.1 $"

class
	CAPTURE_REPLAY_POI

inherit
	CAPTURE_REPLAY_SHARED_INFO	
	
create
	make_with_window
	
feature {NONE} -- Initialization
	
	make_with_window (a: WEL_WINDOW) is
			-- Initialize `Current'.
		do
			set_type (a.generating_type)
			if a.parent /= void then
--				set_parent_identifier (create {like current}.make_with_window (a.parent))
                set_parent_type (a.parent.generating_type)
                set_parent_x (a.parent.x)
				set_parent_y (a.parent.y)
				set_parent_width (a.parent.width)
				set_parent_height (a.parent.height)
			else
				has_no_parent := true
			end
			set_top_x (a.x)
			set_top_y (a.y)
			set_width (a.width)
			set_height (a.height)
		end
		
feature {CAPTURE_REPLAY}
	

	display is
		-- display info about POI
		do
			display_on_file (io.output)
		end
		
	display_on_file (f: PLAIN_TEXT_FILE) is
	-- display binfo about POI
	require
		f /= void and then f.is_open_write
	do
		f.putstring ("%N ----- Type  : ")
		f.putstring (type)
		f.putstring ("%N ----- parent POI  : ")
		if parent_type /= void then
--			parent_identifier.compact_display	
			compact_display_on_file (f)
		else
			f.putstring ("%N ----- This identifier corresponds to root window")
		end
		
		f.putstring ("%N ----- X  : ")
		f.putint (top_x)
		f.putstring ("%N ----- Y : ")
		f.putint (top_y)	
		f.putstring ("%N ----- Width  : ")
		f.putint (width)
		f.putstring ("%N ----- Height : ")
		f.putint (height)
		f.putstring ("%N ------ %N")
	end
	
	compact_display_on_file (f: PLAIN_TEXT_FILE) is
	-- compact display of information
	do
		f.putstring ("%N ----- POI (compact)  : ")
--		f.putstring (type)
		f.putstring (parent_type)
	end

	match (w: WEL_WINDOW): BOOLEAN is
		-- Does the window identifier match to current ?
		do
			Result := w.match_poi (Current)
		end
	
	object : WEL_WINDOW is
		-- find object in memory
		do
			result := widget_by_poi (current)
		end
	
feature {CAPTURE_REPLAY} -- properties of persistent identifier
	
	has_no_parent : BOOLEAN
	-- has current window a parent
	
	type: STRING;
	  -- type name of WEL_WINDOW
	  
--	parent_identifier: CAPTURE_REPLAY_POI
--	  -- persistent identifier of WEL_WINDOW parent
	  
	top_x: INTEGER
	  -- x coordinate of WEL_WINDOW origin point (top left)
	  
	top_y: INTEGER
	  -- y coordinate of WEL_WINDOW origin point (top left)
	
	width: INTEGER
	  -- width of WEL_WINDOW 
	  
	height: INTEGER
	  -- height of WEL_WINDOW 

	parent_type: STRING;
	  -- type name of parent of WEL_WINDOW
	  
	parent_x: INTEGER
	  -- x coordinate of parent of WEL_WINDOW origin point (top left)
	  
	parent_y: INTEGER
	  -- y coordinate of parent of WEL_WINDOW origin point (top left)
	
	parent_width: INTEGER
	  -- width of parent of WEL_WINDOW 
	  
	parent_height: INTEGER
	  -- height of parent of WEL_WINDOW 
	  
feature {NONE} -- Implementation
	
	set_type (a: like type) is
			-- set 'type' with 'a'
		do	
			 type := a
		end
	
--	set_parent_identifier (a: like parent_identifier) is
--			-- set 'parent_identifier' with 'a'
--		do	
--			 parent_identifier := a
--		end		
		
	set_parent_x (a: like parent_x) is
			-- set 'parent_x' with 'a'
		do	
			 parent_x := a
		end	
		
	set_parent_y (a: like parent_y) is
			-- set 'parent_y' with 'a'
		do	
			 parent_y := a
		end	
		
	set_parent_width (a: like parent_width) is
			-- set 'parent_width' with 'a'
		do	
			 parent_width := a
		end	
		
	set_parent_height (a: like parent_height) is
			-- set 'parent_height' with 'a'
		do	
			 parent_height := a
		end	
		
	set_parent_type (a: like parent_type) is
			-- set 'parent_type' with 'a'
		do	
			 parent_type := a
		end	
		
		set_top_x (a: like top_x) is
			-- set 'top_x' with 'a'
		do	
			 top_x := a
		end	
	
	set_top_y (a: like top_y) is
			-- set 'top_y' with 'a'
		do	
			 top_y := a
		end		
	
	set_width (a: like width) is
			-- set 'width' with 'a'
		do	
			 width := a
		end	
	
	set_height (a: like height) is
			-- set 'height' with 'a'
		do	
			 height := a
		end		
		
	invariant
	invariant_clause: True -- Your invariant here
	
	end -- class CAPTURE_REPLAY_POI
