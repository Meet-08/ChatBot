import axios from "axios"

export const loginUser = async (email, password) => {
    const res = await axios.post("/user/login", { email, password });
    if (res.status !== 200) {
        throw new Error("Unable to login");
    }

    const { data } = res;
    return data;
}

export const signupUser = async (name, email, password) => {
    const res = await axios.post("/user/signup", { name, email, password });

    if (res.status !== 200) {
        throw new Error("Unable to signup");
    }

    const { data } = res;
    return data;
}

export const logoutUser = async () => {
    const res = await axios.get("/user/logout");
    if (res.status !== 200) {
        throw new Error("Unable to logout");
    }
}

export const checkAuthStatus = async () => {
    const res = await axios.get("/user/auth-status");
    if (res.status !== 200) {
        throw new Error("Unable to authenticate");
    }

    const { data } = res;
    return data;
}

export const sendChatRequest = async (message) => {
    const res = await axios.post("/chats/new", { message });
    if (res.status !== 200) {
        throw new Error("Unable to send chat");
    }

    const { data } = res;
    return data;
}

export const getPreviousChatRequest = async () => {
    const res = await axios.get("/chats/all-chats");
    if (res.status !== 200) {
        throw new Error("Unable to send chat");
    }

    const { data } = res;
    return data;
}

export const deleteChats = async () => {
    const res = await axios.delete("/chats/delete-chats");
    if (res.status !== 200) {
        throw new Error("Unable to delete chats");
    }

    const { data } = res;
    return data;
}
