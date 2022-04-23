const server_api = 'https://localhost:443/pictures';

function fetchData(cmp, random = false) {
    const date = cmp.state.date ?
        `/${(cmp.state.date + '').replaceAll('-', '').substring(2)}`
        : '';
    fetch(`${server_api}${date}?random=${random}`)
        .then((res) => {
            if (res.ok) {
                return res.json().then(items => {
                    cmp.setState({
                        items,
                        loaded: true,
                    });
                })
            }
            return res.json().then(error => {
                cmp.setState({
                    error,
                    loaded: true,
                });
            })
        });
}
