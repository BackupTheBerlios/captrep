	indexing
		description: "Objects that ..."
		author: ""
		date: "$Date: 2004/05/25 09:29:45 $"
		revision: "$Revision: 1.2 $"
	
	class
		CAPTURE_REPLAY_INFO
	
	inherit
		CAPTURE_REPLAY
		
	create
		make, make_internal
		
	feature
		
		make (e: WEL_MSG; w: WEL_WINDOW; tps : INTEGER) is
				-- initialize infos
				require
					-- w.poi /= void
				do
					message := e.message
					lparam := e.lparam
					wparam := e.wparam
					last_boolean_result := e.last_boolean_result
					dispatch_result := e.dispatch_result
					create target_identifier.make_with_window (w)
					widget := w -- to be removed
					time_replay := tps
				end	
				
		make_internal (w: WEL_WINDOW; m: INTEGER; wp: INTEGER; lp: INTEGER; tps : INTEGER) is
			-- create infos using basic values
				require
					-- w.poi /= void
			do
				create target_identifier.make_with_window (w)
				message := m
				lparam := lp
				wparam := wp
				time_replay := tps
			end
	
	feature {CAPTURE_REPLAY} -- handling of informations related to events
		
		target_identifier : CAPTURE_REPLAY_POI
		-- identifier of event target
	
		message : INTEGER
		lparam : INTEGER
		wparam : INTEGER
		last_boolean_result : BOOLEAN
		dispatch_result : INTEGER
		time_replay : INTEGER -- le temps qu'il faut attendre avant de rejouer l'�v�nement (temps d�compt� par raport au lancement de l'application).
		
		
		set_target_identifier (n: like target_identifier) is
				-- modify 'widget_identifier' with n
				do
					target_identifier := n
				end
			
	feature {CAPTURE_REPLAY} -- to remove
		
		-- Not useful
		widget : WEL_WINDOW
		
	
	
	end -- class CAPTURE_REPLAY_INFO
