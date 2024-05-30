--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: game; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.game (
    rating double precision NOT NULL,
    rating_count integer NOT NULL,
    total_rating double precision NOT NULL,
    total_rating_count integer NOT NULL,
    id bigint NOT NULL,
    cover_url character varying(255),
    game_name character varying(255)
);


ALTER TABLE public.game OWNER TO integration;

--
-- Name: game_genre; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.game_genre (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.game_genre OWNER TO integration;

--
-- Name: game_id_seq; Type: SEQUENCE; Schema: public; Owner: integration
--

CREATE SEQUENCE public.game_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.game_id_seq OWNER TO integration;

--
-- Name: game_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: integration
--

ALTER SEQUENCE public.game_id_seq OWNED BY public.game.id;


--
-- Name: game_mode; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.game_mode (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.game_mode OWNER TO integration;

--
-- Name: game_record; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.game_record (
    game_id bigint,
    id bigint NOT NULL,
    record_month character varying(255),
    record_year character varying(255)
);


ALTER TABLE public.game_record OWNER TO integration;

--
-- Name: game_record_id_seq; Type: SEQUENCE; Schema: public; Owner: integration
--

CREATE SEQUENCE public.game_record_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.game_record_id_seq OWNER TO integration;

--
-- Name: game_record_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: integration
--

ALTER SEQUENCE public.game_record_id_seq OWNED BY public.game_record.id;


--
-- Name: games_genres; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.games_genres (
    game_genre_id bigint NOT NULL,
    game_id bigint NOT NULL
);


ALTER TABLE public.games_genres OWNER TO integration;

--
-- Name: games_modes; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.games_modes (
    game_id bigint NOT NULL,
    game_mode_id bigint NOT NULL
);


ALTER TABLE public.games_modes OWNER TO integration;

--
-- Name: games_perspectives; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.games_perspectives (
    game_id bigint NOT NULL,
    player_perspective_id bigint NOT NULL
);


ALTER TABLE public.games_perspectives OWNER TO integration;

--
-- Name: player_perspective; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.player_perspective (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.player_perspective OWNER TO integration;

--
-- Name: steam_stats; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.steam_stats (
    steam_average_players double precision NOT NULL,
    steam_gain_players double precision NOT NULL,
    steam_peak_players integer NOT NULL,
    game_record_id bigint,
    id bigint NOT NULL,
    steam_avg_peak_perc character varying(255)
);


ALTER TABLE public.steam_stats OWNER TO integration;

--
-- Name: steam_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: integration
--

CREATE SEQUENCE public.steam_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.steam_stats_id_seq OWNER TO integration;

--
-- Name: steam_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: integration
--

ALTER SEQUENCE public.steam_stats_id_seq OWNED BY public.steam_stats.id;


--
-- Name: twitch_stats; Type: TABLE; Schema: public; Owner: integration
--

CREATE TABLE public.twitch_stats (
    twitch_avg_channels integer NOT NULL,
    twitch_avg_viewer_ratio double precision NOT NULL,
    twitch_avg_viewers integer NOT NULL,
    twitch_hours_streamed integer NOT NULL,
    twitch_hours_watched integer NOT NULL,
    twitch_peak_channels integer NOT NULL,
    twitch_peak_viewers integer NOT NULL,
    twitch_streamers integer NOT NULL,
    game_record_id bigint,
    id bigint NOT NULL
);


ALTER TABLE public.twitch_stats OWNER TO integration;

--
-- Name: twitch_stats_id_seq; Type: SEQUENCE; Schema: public; Owner: integration
--

CREATE SEQUENCE public.twitch_stats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.twitch_stats_id_seq OWNER TO integration;

--
-- Name: twitch_stats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: integration
--

ALTER SEQUENCE public.twitch_stats_id_seq OWNED BY public.twitch_stats.id;


--
-- Name: game id; Type: DEFAULT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game ALTER COLUMN id SET DEFAULT nextval('public.game_id_seq'::regclass);


--
-- Name: game_record id; Type: DEFAULT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game_record ALTER COLUMN id SET DEFAULT nextval('public.game_record_id_seq'::regclass);


--
-- Name: steam_stats id; Type: DEFAULT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.steam_stats ALTER COLUMN id SET DEFAULT nextval('public.steam_stats_id_seq'::regclass);


--
-- Name: twitch_stats id; Type: DEFAULT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.twitch_stats ALTER COLUMN id SET DEFAULT nextval('public.twitch_stats_id_seq'::regclass);


--
-- Name: game_genre game_genre_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game_genre
    ADD CONSTRAINT game_genre_pkey PRIMARY KEY (id);


--
-- Name: game_mode game_mode_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game_mode
    ADD CONSTRAINT game_mode_pkey PRIMARY KEY (id);


--
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);


--
-- Name: game_record game_record_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game_record
    ADD CONSTRAINT game_record_pkey PRIMARY KEY (id);


--
-- Name: player_perspective player_perspective_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.player_perspective
    ADD CONSTRAINT player_perspective_pkey PRIMARY KEY (id);


--
-- Name: steam_stats steam_stats_game_record_id_key; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.steam_stats
    ADD CONSTRAINT steam_stats_game_record_id_key UNIQUE (game_record_id);


--
-- Name: steam_stats steam_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.steam_stats
    ADD CONSTRAINT steam_stats_pkey PRIMARY KEY (id);


--
-- Name: twitch_stats twitch_stats_game_record_id_key; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.twitch_stats
    ADD CONSTRAINT twitch_stats_game_record_id_key UNIQUE (game_record_id);


--
-- Name: twitch_stats twitch_stats_pkey; Type: CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.twitch_stats
    ADD CONSTRAINT twitch_stats_pkey PRIMARY KEY (id);


--
-- Name: games_genres fk3nj7g4ai793jra2wh41ens1q3; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_genres
    ADD CONSTRAINT fk3nj7g4ai793jra2wh41ens1q3 FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- Name: games_perspectives fka0uqt9s62segpfuk7cjxe8r53; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_perspectives
    ADD CONSTRAINT fka0uqt9s62segpfuk7cjxe8r53 FOREIGN KEY (player_perspective_id) REFERENCES public.player_perspective(id);


--
-- Name: games_perspectives fkbcqluf2mufe6amqqxcuiw0m8d; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_perspectives
    ADD CONSTRAINT fkbcqluf2mufe6amqqxcuiw0m8d FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- Name: game_record fkcjj1jeto0f8fragnqxocf5jh4; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.game_record
    ADD CONSTRAINT fkcjj1jeto0f8fragnqxocf5jh4 FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- Name: games_modes fkfuc9p6n5b1302xtr8hu8m0ya; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_modes
    ADD CONSTRAINT fkfuc9p6n5b1302xtr8hu8m0ya FOREIGN KEY (game_id) REFERENCES public.game(id);


--
-- Name: games_genres fkjcgnt9tnpjkwurfy0vkk31u0l; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_genres
    ADD CONSTRAINT fkjcgnt9tnpjkwurfy0vkk31u0l FOREIGN KEY (game_genre_id) REFERENCES public.game_genre(id);


--
-- Name: games_modes fkkymgheppke18bcx69c4sjkv6r; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.games_modes
    ADD CONSTRAINT fkkymgheppke18bcx69c4sjkv6r FOREIGN KEY (game_mode_id) REFERENCES public.game_mode(id);


--
-- Name: steam_stats fklkk31s3b7ibq2np2p5qjm7e2w; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.steam_stats
    ADD CONSTRAINT fklkk31s3b7ibq2np2p5qjm7e2w FOREIGN KEY (game_record_id) REFERENCES public.game_record(id);


--
-- Name: twitch_stats fko0qfgrgrajj6qkvbma7itst68; Type: FK CONSTRAINT; Schema: public; Owner: integration
--

ALTER TABLE ONLY public.twitch_stats
    ADD CONSTRAINT fko0qfgrgrajj6qkvbma7itst68 FOREIGN KEY (game_record_id) REFERENCES public.game_record(id);


--
-- PostgreSQL database dump complete
--

