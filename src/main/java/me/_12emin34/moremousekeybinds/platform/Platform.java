package me._12emin34.moremousekeybinds.platform;

public interface Platform {
	boolean isModLoaded(String modId);

	boolean isDebug();

	ModLoader loader();

	String mcVersion();

	String packPath(VersionedPackType versionedPackType);

	enum ModLoader {
		FABRIC, NEOFORGE, FORGE
	}

	enum VersionedPackType {
		ASSETS("rp"),
		DATA("dp");

		private final String path;

		VersionedPackType(String path) {
			this.path = path;
		}

		public String getName() {
			return "/" + path;
		}
	}
}
