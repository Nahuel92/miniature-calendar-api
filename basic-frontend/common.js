function errorMessage(component) {
    return e('div',
        {},
        component.state.error.error,
        e('button',
            {onClick: () => component.setState({error: null, isPressed: false})},
            component.hideText
        )
    );
}

function fetchData(cmp, date = null, random = false) {
    const dat = date ? `/${date}` : '';
    fetch(`http://localhost:8080/picture${dat}?random=${random}`)
        .then((res) => {
            if (res.ok) {
                return res.json().then(result => {
                    cmp.setState({
                        items: result,
                        loaded: true
                    });
                })
            }
            return res.json().then(error => {
                cmp.setState({
                    loaded: true,
                    error
                });
            })
        });
}

function showData(state) {
    const g = state.items.map(function (d) {
        return [e('a', {href: d.url, key: d.url}, d.url),
            e('img',
                {src: `data:image/jpg;base64,${d.picture}`, key: d.url + d.url},
                null
            )];
    });
    const cnt = document.querySelector('#content');
    ReactDOM.render(g, cnt);
}

function hideData() {
    const cnt = document.querySelector('#content');
    ReactDOM.render(e('div', {}), cnt);
}
