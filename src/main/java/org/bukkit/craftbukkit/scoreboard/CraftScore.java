package org.bukkit.craftbukkit.scoreboard;

import java.util.Map;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

/**
 * TL;DR: This class is special and lazily grabs a handle...
 * ...because a handle is a full fledged (I think permanent) hashMap for the associated name.
 * <p>
 * Also, as an added perk, a CraftScore will (intentionally) stay a valid reference so long as objective is valid.
 */
final class CraftScore implements Score {
    private final String entry;
    private final CraftObjective objective;

    CraftScore(CraftObjective objective, String entry) {
        this.objective = objective;
        this.entry = entry;
    }

    @Override
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(entry);
    }

    @Override
    public String getEntry() {
        return entry;
    }

    @Override
    public Objective getObjective() {
        return objective;
    }

    @Override
    public int getScore() {
        Scoreboard board = objective.checkState().board;

        if (board.getKnownPlayers().contains(entry)) { // Lazy
            Map<ScoreboardObjective, ScoreboardPlayerScore> scores = board.getPlayerObjectives(entry);
            ScoreboardPlayerScore score = scores.get(objective.getHandle());
            if (score != null) { // Lazy
                return score.getScore();
            }
        }

        return 0; // Lazy
    }

    @Override
    public void setScore(int score) {
        objective.checkState().board.getPlayerScore(entry, objective.getHandle()).setScore(score);
    }

    @Override
    public boolean isScoreSet() {
        Scoreboard board = objective.checkState().board;

        return board.getKnownPlayers().contains(entry) && board.getPlayerObjectives(entry).containsKey(objective.getHandle());
    }

    @Override
    public CraftScoreboard getScoreboard() {
        return objective.getScoreboard();
    }
}
